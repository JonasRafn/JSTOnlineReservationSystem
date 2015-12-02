package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import deploy.DeploymentConfiguration;
import dto.AirlineDTO;
import dto.FlightDTO;
import entity.AirlineApi;
import entity.Airport;
import exception.BadRequestException;
import exception.NoResultException;
import interfaces.IFlightFacade;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import exception.NotFoundException;
import exception.ServerException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.ws.rs.core.Response;
import service.getFlights;

/**
 * http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/JAXRS2/jaxrs-clients.html
 *
 */
public class FlightFacade implements IFlightFacade {

    private Gson gson;
    private final int TIMEOUT_DELAY = 5; //seconds
    private EntityManagerFactory emf;
    private Map<String, Airport> airports;

    public FlightFacade(EntityManagerFactory emf) {
        this.emf = emf;
        airports = cacheAirports();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
    }

    @Override
    public List<AirlineDTO> getFlights(String from, String to, String stringDate, int numTickets) throws NotFoundException, NoResultException, BadRequestException, ServerException {
        List<AirlineDTO> airlines = new ArrayList();
        List<Future<Response>> airlineList = new ArrayList();
        List<AirlineApi> airlineApiList = getAirlineApiList();

        //validate airports
        if (!airports.containsKey(to) && !to.isEmpty()) {
            throw new NotFoundException("Unknown destination airport: " + to);
        } else if (!airports.containsKey(from)) {
            throw new NotFoundException("Unknown origin airport: " + from);
        }
        //validate date
        DateFormat ISO8601Date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date date = ISO8601Date.parse(stringDate);
        } catch (ParseException ex) {
            throw new BadRequestException("Invalid date");
        }

        //if not empty, we get flights with a [to] also
        if (!to.isEmpty()) {
            to = "/" + to;
        }

        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (AirlineApi api : airlineApiList) {
            String url = api.getUrl() + "api/flightinfo/" + from + to + "/" + stringDate + "/" + numTickets;
            Future<Response> future = executor.submit(new getFlights(url));
            airlineList.add(future);
        }
        executor.shutdown();

        for (Future<Response> r : airlineList) {
            AirlineDTO airline = new AirlineDTO();
            try {
                Response response = r.get(TIMEOUT_DELAY, TimeUnit.SECONDS);
                int status = response.getStatus();
                String re = response.readEntity(String.class);
                switch (status) {
                    //case 400: 1. No flights from BCN at the given date, 2. Flight is sold out, or not enough avilable tickets
                    //case 404: 2. HTTP 404 Not Found
                    //case 500: 3. Internal server error
                    case 200:
                        // OK
                        JsonObject jo = gson.fromJson(re, JsonObject.class);
                        airline = new AirlineDTO(gson.fromJson(jo.get("airline").toString(), String.class)); //save airline name
                        for (JsonElement element : jo.getAsJsonArray("flights")) { //save flights
                            JsonObject asJsonObject = element.getAsJsonObject();
                            FlightDTO dto = gson.fromJson(asJsonObject, FlightDTO.class);
                            dto.setDestinationCity(airports.get(dto.getDestination()).getCity());
                            dto.setOriginCity(airports.get(dto.getOrigin()).getCity());
                            dto.setDestinationDate(calculateLocalTime(airports.get(dto.getOrigin()).getTimeZone(), airports.get(dto.getDestination()).getTimeZone(), dto.getTraveltime(), dto.getDate()));
                            airline.addFlights(dto);
                            dto.getTotalPrice();
                        }
                        airlines.add(airline);
                        break;
                    default:
                        break;
                }
            } catch (InterruptedException ex) {
                System.out.println("##--Thread interrupted--##");
                //do nothing
            } catch (ExecutionException ex) {
                System.out.println("##--Execution interrupted--##");
                //do nothing
            } catch (TimeoutException ex) {
                System.out.println("##--Call timeout--##");
                //do nothing
            }
        }
        if (airlines.isEmpty()) {
            /*message stolen from momondo...
            "Oops! No flights were found...\n"
            + "We weren't able to find any flights matching your request.\n"
            + "Please try again, perhaps with alternative dates or airports.");*/
            throw new NoResultException();
        }
        return airlines;
    }

    private List<AirlineApi> getAirlineApiList() throws ServerException {
        EntityManager em = getEntityManager();
        List<AirlineApi> airlineApiList = new ArrayList();
        try {
            TypedQuery<AirlineApi> query = em.createNamedQuery("AirlineApi.findAll", AirlineApi.class);
            airlineApiList = query.getResultList();
            if (airlineApiList.isEmpty()) {
                throw new ServerException("Could not load Airlines");
            }
        } finally {
            em.close();
        }
        return airlineApiList;
    }

    private Date calculateLocalTime(String originTZ, String destinationTZ, int travelTime, Date travelDate) {
        TimeZone originTimeZone = TimeZone.getTimeZone(originTZ);
        TimeZone destinationTimeZone = TimeZone.getTimeZone(destinationTZ);
        int offset = destinationTimeZone.getRawOffset() - originTimeZone.getRawOffset() + (travelTime * 60000);
        Date adjustedDate = new Date(travelDate.getTime() + offset);
        return adjustedDate;
    }

    private Map<String, Airport> cacheAirports() {
        EntityManager em = getEntityManager();
        Map<String, Airport> airportMap = new HashMap();
        List<Airport> airportList = new ArrayList();
        try {
            TypedQuery<Airport> query = em.createNamedQuery("Airport.findAll", Airport.class);
            airportList = query.getResultList();
            if (airportMap.isEmpty()) {
                //throw some exception;
            }
            for (Airport a : airportList) {
                airportMap.put(a.getIATACode(), a);
            }
        } finally {
            em.close();
        }
        return airportMap;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}

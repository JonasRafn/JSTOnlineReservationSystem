package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import dto.AirlineDTO;
import dto.FlightDTO;
import entity.AirlineApi;
import entity.Airport;
import entity.SearchRequest;
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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import exception.NotFoundException;
import exception.ServerException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.ws.rs.core.Response;
import service.GetFlights;
import utility.Airports;

/**
 * http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/JAXRS2/jaxrs-clients.html
 *
 */
public class FlightFacade implements IFlightFacade {

    private Gson gson;
    private final int TIMEOUT_DELAY = 10; //seconds
    private EntityManagerFactory emf;
    private Map<String, Airport> airports;

    public FlightFacade(EntityManagerFactory emf) {
        this.emf = emf;
        airports = Airports.getAirports();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
    }

    /**
     * get all available flights from all airlines
     *
     * 1. Check if airport to and from exists in DB, otherwise throw exception
     * 2. Check if date is valid, otherwise throw exception 3. Execute the
     * callable "GetFlights.class" which takes each airline-api url as input and
     * output a Response object. 4. Loop through all futures and use the
     * Response to return a list of dto-airlines, each containing a list of
     * dto-flights.
     *
     * @param request
     * @return list containing AirlineDTOs
     * @throws NotFoundException
     * @throws NoResultException
     * @throws BadRequestException
     * @throws ServerException
     */
    @Override
    public List<AirlineDTO> getFlights(SearchRequest request) throws NotFoundException, NoResultException, BadRequestException, ServerException {
        List<AirlineDTO> airlines = new ArrayList();
        List<Future<Response>> airlineList = new ArrayList();
        List<AirlineApi> airlineApiList = getAirlineApiList();

        String from = request.getOrigin();
        String to;
        if (request.getDestination() != null) {
            to = request.getDestination();
        } else {
            to = "";
        }
        String stringDate = request.getDate();
        int numTickets = request.getNumberOfTickets();
        saveSearchRequest(request);

        //validate airports
        if (airports.isEmpty()) {
            throw new ServerException("Something went wrong. Please try again");
        }
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

        ExecutorService executor = Executors.newFixedThreadPool(12);
        for (AirlineApi api : airlineApiList) {
            String url = api.getUrl() + "api/flightinfo/" + from + to + "/" + stringDate + "/" + numTickets;
            Future<Response> future = executor.submit(new GetFlights(url));
            airlineList.add(future);
        }
        executor.shutdown();

        int testNo = 1; // testing purposes only
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
                    case 200: // OK
                        JsonObject jo = gson.fromJson(re, JsonObject.class);
                        airline = new AirlineDTO(gson.fromJson(jo.get("airline").toString(), String.class) + "-TestAirlineNo: " + testNo++); //save airline name
                        for (JsonElement element : jo.getAsJsonArray("flights")) { //save flights
                            JsonObject asJsonObject = element.getAsJsonObject();
                            FlightDTO dto = gson.fromJson(asJsonObject, FlightDTO.class);
                            dto.calculatePricePerPerson(); // calcaute price per person
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
            } catch (JsonSyntaxException ex) {
                System.out.println("##--Wrong Json syntax--##");
                //just skip that airline
            }
        }
        if (airlines.isEmpty()) {
            throw new NoResultException();
        }
        return airlines;
    }

    /**
     * get all AirlineAPI entities from DB
     *
     * @return list containing AirlineApi objects
     * @throws ServerException if returned list is empty
     */
    private List<AirlineApi> getAirlineApiList() throws ServerException {
        EntityManager em = getEntityManager();
        List<AirlineApi> airlineApiList = new ArrayList();
        try {
            TypedQuery<AirlineApi> query = em.createNamedQuery("AirlineApi.findAll", AirlineApi.class);
            airlineApiList = query.getResultList();
            if (airlineApiList.isEmpty()) {
                throw new ServerException("Something went wrong. Please try again");
            }
        } finally {
            em.close();
        }
        return airlineApiList;
    }

    /**
     * Calculates local datetime in destination, taking travel time into account
     *
     * @param originTZ origin timezone
     * @param destinationTZ destination timezone
     * @param travelTime travel time in minutes
     * @param travelDate travel date from origin
     * @return local datetime in destination
     */
    private Date calculateLocalTime(String originTZ, String destinationTZ, int travelTime, Date travelDate) {
        TimeZone originTimeZone = TimeZone.getTimeZone(originTZ);
        TimeZone destinationTimeZone = TimeZone.getTimeZone(destinationTZ);
        int offset = destinationTimeZone.getRawOffset() - originTimeZone.getRawOffset() + (travelTime * 60000);
        Date adjustedDate = new Date(travelDate.getTime() + offset);
        return adjustedDate;
    }

    private void saveSearchRequest(SearchRequest request) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(request);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}

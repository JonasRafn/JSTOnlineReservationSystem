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
import exception.BadRequestException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import service.getFlights;

/**
 * http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/JAXRS2/jaxrs-clients.html
 *
 */
public class FlightFacade implements IFlightFacade {

    private Gson gson;

    private EntityManagerFactory emf;
    private Map<String, Airport> airports;

    public FlightFacade(EntityManagerFactory emf) {
        this.emf = emf;
        airports  = cacheAirports();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
    }

    @Override
    public List<AirlineDTO> getFlights(String from, String to, String stringDate, int numTickets) throws BadRequestException {

        List<AirlineDTO> airlines = new ArrayList();
        List<Future<String>> airlineList = new ArrayList();
        List<AirlineApi> airlineApiList = getAirlineApiList();
        
        //validate airports
        if (!airports.containsKey(to)) {
           // throw AirportExistsException("Uknown airport: " + to);
        } else if (!airports.containsKey(from)) {
            // throw AirportExistsException("Uknown airport: " + from);
        }
        //validate date
        

        //if not empty, we get flights with a to also
        if (!to.isEmpty()) {
            to = "/" + to;
        }

        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (AirlineApi api : airlineApiList) {
            String url = api.getUrl() + "api/flightinfo/" + from + to + "/" + stringDate + "/" + numTickets;
            Future<String> future = executor.submit(new getFlights(url));
            airlineList.add(future);
        }
        executor.shutdown();

        for (Future<String> r : airlineList) {
            AirlineDTO airline = new AirlineDTO();
            try {
                String response = r.get(2, TimeUnit.SECONDS);
                JsonObject jo = gson.fromJson(response, JsonObject.class);

                airline = new AirlineDTO(gson.fromJson(jo.get("airline").toString(), String.class)); //save airline name
                for (JsonElement element : jo.getAsJsonArray("flights")) { //save flights
                    JsonObject asJsonObject = element.getAsJsonObject();
                    FlightDTO dto = gson.fromJson(asJsonObject, FlightDTO.class);
                    airline.addFlights(dto);
                    dto.getTotalPrice();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(FlightFacade.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                Logger.getLogger(FlightFacade.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(FlightFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
            airlines.add(airline);
        }
        return airlines;
    }

    private List<AirlineApi> getAirlineApiList() {
        EntityManager em = getEntityManager();
        List<AirlineApi> airlineApiList = new ArrayList();
        try {
            TypedQuery<AirlineApi> query = em.createNamedQuery("AirlineApi.findAll", AirlineApi.class);
            airlineApiList = query.getResultList();
            if (airlineApiList.isEmpty()) {
                //throw new Exception("error");
            }
        } finally {
            em.close();
        }
        return airlineApiList;
    }

    @Override
    public Date calculateLocalTime(String originTZ, String destinationTZ, Date date) throws ParseException {
        TimeZone originTimeZone = TimeZone.getTimeZone(originTZ);
        TimeZone destinationTimeZone = TimeZone.getTimeZone(destinationTZ);
        int offset = destinationTimeZone.getRawOffset() - originTimeZone.getRawOffset();
        Date adjustedDate = new Date(date.getTime() + offset);
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

    private void calculateLocalTime() throws ParseException {
        DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date2 = sdfISO.parse("2001-07-04T12:08:56.235-0000");
        System.out.println(date2);

        // 2016-01-01T00:00:00.000Z
//        Date start = sdfISO.parse("2016-01-01T00:00:00.000");
        //      Date stop = sdfISO.parse("2016-01-01T19:00:00.000");
//        Long test = stop.getTime() - start.getTime();
        TimeZone timeZone1 = TimeZone.getTimeZone("Europe/Berlin"); // SXF
        TimeZone timeZone2 = TimeZone.getTimeZone("Europe/Copenhagen"); // CPH
        TimeZone timeZone3 = TimeZone.getTimeZone("Asia/Chongqing"); // FUO
        int rawOffset = timeZone3.getRawOffset();
        System.out.println(rawOffset);
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void main(String[] args) throws ParseException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        FlightFacade ctrl = new FlightFacade(emf);
//        ctrl.calculateLocalTime();

    }
}

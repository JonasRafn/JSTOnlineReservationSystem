package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.AirlineDTO;
import dto.FlightDTO;
import dto.SearchRequestDTO;
import entity.AirlineApi;
import entity.Airport;
import entity.SearchRequest;
import exception.NoResultException;
import interfaces.IFlightFacade;
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
import exception.ServerException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.ws.rs.core.Response;
import service.GetFlights;
import utility.Airports;
import static utility.ErrorCodes.getServerErrorMsg;
import utility.Validator;

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
     */
    @Override
    public List<AirlineDTO> getFlights(SearchRequestDTO request) throws Exception {
        List<AirlineDTO> airlines = new ArrayList();
        List<Future<Response>> airlineList = new ArrayList();
        List<AirlineApi> airlineApiList = getAirlineApiList();

        String from = request.getOrigin();
        String to = "";
        if (!request.getDestination().isEmpty()) {
            to = request.getDestination();
        }
        String stringDate = request.getDate();
        int numTickets = request.getNumberOfTickets();
        
        saveSearchRequest(request); //historical data

        //validate airports
        if (airports.isEmpty()) {
            throw new ServerException(getServerErrorMsg("Could not load airports."));
        }
        if (!airports.containsKey(to) && !to.isEmpty()) {
            throw new ServerException(getServerErrorMsg("Unknown destination airport: " + to));
        } else if (!airports.containsKey(from)) {
            throw new ServerException(getServerErrorMsg("Unknown origin airport: " + from));
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

        // 'continue;' skips object in loop and go to next
        for (Future<Response> r : airlineList) {
            AirlineDTO airlineDTO = new AirlineDTO();

            Response response;
            try {
                response = r.get(TIMEOUT_DELAY, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                System.out.println("##--Something went wrong when processing api-call--##\n" + ex);
                continue;
            }
            int status = response.getStatus();
            String re = response.readEntity(String.class);
            switch (status) {
                case 200: // if OK
                    try { //validate json
                        airlineDTO = Validator.validateJson(re);
                    } catch (ServerException ex) {
                        System.out.println("##--Wrong Json syntax--##");
                        continue;
                    }
                    //add the remaining properties to the airlineDTO
                    for (FlightDTO dto : airlineDTO.getFlights()) {
                        dto.calculatePricePerPerson(); // calculate price per person
                        try {
                            dto.setDestinationCity(airports.get(dto.getDestination()).getCity());
                            dto.setOriginCity(airports.get(dto.getOrigin()).getCity());
                            dto.setDestinationDate(calculateLocalTime(airports.get(dto.getOrigin()).getTimeZone(), airports.get(dto.getDestination()).getTimeZone(), dto.getTraveltime(), dto.getDate()));
                        } catch (NullPointerException ex) { //unknown city
                            System.out.println("##--Unknown destination/timezone##");
                            continue;
                        }
                    }
                    airlines.add(airlineDTO);
                    break;
                default:
                    break;
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
    private List<AirlineApi> getAirlineApiList() throws Exception {
        EntityManager em = getEntityManager();
        List<AirlineApi> airlineApiList = new ArrayList();
        try {
            TypedQuery<AirlineApi> query = em.createNamedQuery("AirlineApi.findAll", AirlineApi.class);
            airlineApiList = query.getResultList();
            if (airlineApiList.isEmpty()) {
                throw new ServerException(getServerErrorMsg("Could not load airlines"));
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
        System.out.println("Adjusted date: " + adjustedDate.toString() + " from: " + originTZ + " to: " + destinationTZ + ", traveltime: " + travelTime + " and travelDate: " + travelDate.toString());
        return adjustedDate;
    }

    private void saveSearchRequest(SearchRequestDTO dto) {
        EntityManager em = getEntityManager();
        SearchRequest request = new SearchRequest(dto.getOrigin(), dto.getDestination(), dto.getDate(), dto.getNumberOfTickets());
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

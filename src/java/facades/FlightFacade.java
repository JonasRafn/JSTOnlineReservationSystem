package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dto.AirlineDTO;
import dto.FlightDTO;
import entity.AirlineApi;
import interfaces.IFlightFacade;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import service.getFlights;

/**
 * http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/JAXRS2/jaxrs-clients.html
 *
 */
public class FlightFacade implements IFlightFacade {

    private Gson gson;
    private EntityManagerFactory emf;

    public FlightFacade(EntityManagerFactory emf) {
        this.emf = emf;
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
    }

    @Override
    public List<AirlineDTO> getFlights(String from, String to, String stringDate, int numTickets) throws Exception {
        List<AirlineDTO> airlines = new ArrayList();
        List<Future<String>> airlineList = new ArrayList();
        List<AirlineApi> airlineApiList = getAirlineApiList();

        //validation
        DateFormat isoDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        try {
            Date date = isoDate.parse(stringDate);
        } catch (ParseException ex) {
        }

        //if not empty, we get flights with a to also
        if (!to.isEmpty()) {
            to = "/" + to;
            System.out.println(to);
        }

        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (AirlineApi api : airlineApiList) {
            String url = api.getUrl() + "api/flightinfo/" + from + to + "/" + stringDate + "/" + numTickets;
            System.out.println(url);
            Future<String> future = executor.submit(new getFlights(url));
            airlineList.add(future);
        }
        executor.shutdown();

        for (Future<String> r : airlineList) {
            AirlineDTO airline = new AirlineDTO();
            try {
                String response = r.get();
                JsonObject jo = gson.fromJson(response, JsonObject.class);
                airline = new AirlineDTO(gson.fromJson(jo.get("airline").toString(), String.class)); //save airline name
                for (JsonElement element : jo.getAsJsonArray("flights")) { //save flights
                    JsonObject asJsonObject = element.getAsJsonObject();
                    FlightDTO dto = gson.fromJson(asJsonObject, FlightDTO.class);
                    airline.addFlights(dto);
                }
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(FlightFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
            airlines.add(airline);
        }
        return airlines;
    }

    private List<AirlineApi> getAirlineApiList() throws Exception {
        EntityManager em = getEntityManager();
        List<AirlineApi> airlineApiList = new ArrayList();
        try {
            TypedQuery<AirlineApi> query = em.createNamedQuery("AirlineApi.findAll", AirlineApi.class);
            airlineApiList = query.getResultList();
            if (airlineApiList.isEmpty()) {
                throw new Exception("error");
            }
        } finally {
            em.close();
        }
        return airlineApiList;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}

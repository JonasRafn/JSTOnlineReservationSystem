package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dto.AirlineDTO;
import dto.FlightDTO;
import entity.AirlineApi;
import interfaces.IFlightFacade;
import java.util.ArrayList;
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

    private List<String> testAirlinesUrls;
    private Gson gson;
    private EntityManagerFactory emf;

    public FlightFacade(EntityManagerFactory emf) {
        this.emf = emf;
        testAirlinesUrls = new ArrayList();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
        testAirlinesUrls.add("http://angularairline-plaul.rhcloud.com/api/flightinfo/");
        testAirlinesUrls.add("http://angularairline-plaul.rhcloud.com/api/flightinfo/");
        testAirlinesUrls.add("http://angularairline-plaul.rhcloud.com/api/flightinfo/");
    }

    @Override
    public List<AirlineDTO> getFlightFrom(String from, String date, int numTickets) throws Exception {
        EntityManager em = getEntityManager();
        List<AirlineDTO> airlines = new ArrayList();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<String>> airlineList = new ArrayList();
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

        for (AirlineApi api : airlineApiList) {
            String url = api.getUrl() + "api/flightinfo/" + from + "/" + date + "/" + numTickets;
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


    @Override
    public List<AirlineDTO> getFlightFromTo(String from, String to, String date, int numTickets) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}

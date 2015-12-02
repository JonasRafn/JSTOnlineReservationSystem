package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dto.AirlineDTO;
import dto.FlightDTO;
import interfaces.IFlightFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/JAXRS2/jaxrs-clients.html
 *
 */
public class FlightFacade implements IFlightFacade {

    private List<String> testAirlinesUrls;
    private Gson gson;

    public FlightFacade() {
        testAirlinesUrls = new ArrayList();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
        testAirlinesUrls.add("http://angularairline-plaul.rhcloud.com/api/flightinfo/");
        testAirlinesUrls.add("http://angularairline-plaul.rhcloud.com/api/flightinfo/");
        testAirlinesUrls.add("http://angularairline-plaul.rhcloud.com/api/flightinfo/");
    }

    //target = client.target(url + from + "/" + date + "/" + numTickets);
    @Override
    public List<AirlineDTO> getFlightFrom(String from, String date, int numTickets) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        return null;
    }
}

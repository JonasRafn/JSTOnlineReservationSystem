package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.HistoryDTO;
import facades.HistoryFacade;
import interfaces.IHistoryFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utility.EntityManagerFactoryProvider;

@Path("history")
public class HistoryRest {

    private IHistoryFacade ctrl;
    private Gson gson;
    private EntityManagerFactory emf;

    public HistoryRest() {
        emf = EntityManagerFactoryProvider.getEntityManagerFactory();
        ctrl = new HistoryFacade(emf);
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistory() {
        HistoryDTO history = ctrl.getSearchHistory();
        return Response.ok(gson.toJson(history)).build();
        
    }
}

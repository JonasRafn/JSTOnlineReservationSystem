package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dto.HistoryDTO;
import entity.AirlineApi;
import exception.AirlineAlreadyExistException;
import facades.DashboardFacade;
import interfaces.IDashboardFacade;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import utility.EntityManagerFactoryProvider;

@Path("dashboard")
@RolesAllowed("Admin")
public class DashboardRest {

    private IDashboardFacade ctrl;
    private Gson gson;
    private EntityManagerFactory emf;

    @Context
    private UriInfo context;
    
    public DashboardRest() {
        emf = EntityManagerFactoryProvider.getEntityManagerFactory();
        ctrl = new DashboardFacade(emf);
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @GET
    @Path("history")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistory() throws Exception {
        HistoryDTO history = ctrl.getSearchHistory();
        return Response.ok(gson.toJson(history)).build();
        
    }
}

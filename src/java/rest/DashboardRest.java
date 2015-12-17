package rest;

import dto.HistoryDTO;
import facades.DashboardFacade;
import interfaces.IDashboardFacade;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import utility.EntityManagerFactoryProvider;
import static utility.JsonConverter.toJsonHistoryDTO;

@Path("dashboard")
@RolesAllowed("Admin")
public class DashboardRest {

    private final IDashboardFacade ctrl;
    private final EntityManagerFactory emf;

    @Context
    private UriInfo context;
    
    public DashboardRest() {
        emf = EntityManagerFactoryProvider.getEntityManagerFactory();
        ctrl = new DashboardFacade(emf);
    }

    @GET
    @Path("history")
    public Response getHistory() throws Exception {
        HistoryDTO history = ctrl.getSearchHistory();
        return Response.ok(toJsonHistoryDTO(history)).type(MediaType.APPLICATION_JSON).build();
        
    }
}

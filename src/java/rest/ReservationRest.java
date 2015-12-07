package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import deploy.DeploymentConfiguration;
import entity.Reservation;
import facades.ReservationFacade;
import interfaces.IReservationFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("reservation")
public class ReservationRest {
    
    private IReservationFacade ctrl;
    private Gson gson;
    private EntityManagerFactory emf;
    
    public ReservationRest() {
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        ctrl = new ReservationFacade(emf);
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
    }
    
    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservations(@PathParam("username") String username) {
        List<Reservation> list = ctrl.getReservations(username);
        return Response.ok(gson.toJson(list)).build();
    }
    
}

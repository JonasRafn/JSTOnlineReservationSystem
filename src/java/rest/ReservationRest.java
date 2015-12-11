package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Reservation;
import exception.NoResultException;
import exception.ReservationException;
import exception.ServerException;
import facades.ReservationFacade;
import interfaces.IReservationFacade;
import java.io.IOException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import static utility.JsonConverter.toJson;

@Path("reservation")
public class ReservationRest {

    private final IReservationFacade ctrl;
    private final Gson gson;

    public ReservationRest() {
        ctrl = new ReservationFacade();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
    }

    @GET
    @RolesAllowed({"User", "Admin"})
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersReservations(@PathParam("username") String username) throws NoResultException, Exception {
        List<Reservation> list = ctrl.getReservations(username);
        System.out.println(toJson(list));
        return Response.ok(toJson(list)).build();
    }
    
    @POST
    @RolesAllowed({"User"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response reserveTickets(String reservation) throws IOException, ServerException, ReservationException, Exception {
        ctrl.reserveTickets(gson.fromJson(reservation, Reservation.class));
        String success = "{\"message\":\"Tickets succesfully reserved\"}";
        return Response.status(Status.OK).entity(success).build();
    }
    
    @DELETE
    @RolesAllowed("Admin")
    @Path("{reservationID}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteReservation(@PathParam("reservationID") long reservationID) throws NoResultException, Exception {
        ctrl.deleteReservation(reservationID);
        String success = "{\"message\":\"Reservation succesfully removed\"}";
        return Response.status(Status.OK).entity(success).build();
    }
}

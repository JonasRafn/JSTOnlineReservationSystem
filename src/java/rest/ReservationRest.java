package rest;

import entity.Reservation;
import facades.ReservationFacade;
import interfaces.IReservationFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import static utility.JsonConverter.fromJsonReservation;
import static utility.JsonConverter.toJsonReservationList;

@Path("reservation")
public class ReservationRest {

    private final IReservationFacade ctrl;

    public ReservationRest() {
        ctrl = new ReservationFacade();
    }

    @GET
    @RolesAllowed({"User", "Admin"})
    @Path("{username}")
    public Response getUsersReservations(@PathParam("username") String username) throws Exception {
        List<Reservation> list = ctrl.getReservations(username);
        return Response.ok(toJsonReservationList(list)).type(MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @RolesAllowed({"User"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response reserveTickets(String reservation) throws Exception {
        ctrl.reserveTickets(fromJsonReservation(reservation));
        String success = "{\"message\":\"Tickets succesfully reserved\"}";
        return Response.status(Status.OK).entity(success).type(MediaType.APPLICATION_JSON).build();
    }
    
    @DELETE
    @RolesAllowed("Admin")
    @Path("{reservationID}")
    public Response deleteReservation(@PathParam("reservationID") long reservationID) throws Exception {
        ctrl.deleteReservation(reservationID);
        String success = "{\"message\":\"Reservation succesfully removed\"}";
        return Response.status(Status.OK).entity(success).type(MediaType.APPLICATION_JSON).build();
    }
}

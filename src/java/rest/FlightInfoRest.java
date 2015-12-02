package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.AirlineDTO;
import facades.FlightFacade;
import interfaces.IFlightFacade;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("flightinfo")
public class FlightInfoRest {

    private IFlightFacade ctrl;
    private Gson gson;

    public FlightInfoRest() {
        ctrl = new FlightFacade();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
    }

    @GET
    @Path("{from}/{date}/{persons}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFlightsFrom(@PathParam("from") String from, @PathParam("date") String stringDate, @PathParam("persons") int persons) {
        DateFormat isoDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        try {
            Date date = isoDate.parse(stringDate);
        } catch (ParseException ex) {
        }
        try {
            List<AirlineDTO> flightsFrom = ctrl.getFlightFrom(from, stringDate, persons);
            return Response.ok(gson.toJson(flightsFrom)).build();
        } catch (Exception e) {
            return null;
        }
    }
}

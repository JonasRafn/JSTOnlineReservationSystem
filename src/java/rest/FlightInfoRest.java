package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import deploy.DeploymentConfiguration;
import dto.AirlineDTO;
import exception.BadRequestException;
import exception.NoResultException;
import exception.NotFoundException;
import exception.ServerException;
import facades.FlightFacade;
import interfaces.IFlightFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
    private EntityManagerFactory emf;
    
    public FlightInfoRest() {
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        ctrl = new FlightFacade(emf);
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
    }

    @GET
    @Path("{from}/{date}/{persons}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFlightsFrom(@PathParam("from") String from, @PathParam("date") String stringDate, @PathParam("persons") int persons)
            throws NotFoundException, NoResultException, BadRequestException, ServerException {
        try {
            List<AirlineDTO> flightsFrom = ctrl.getFlights(from, "", stringDate, persons);
            return Response.ok(gson.toJson(flightsFrom)).build();
        } finally {
        }
    }

    @GET
    @Path("{from}/{to}/{date}/{persons}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFlightsFromTo(@PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String stringDate, @PathParam("persons") int persons)
            throws NotFoundException, NoResultException, BadRequestException, ServerException {
        try {
            List<AirlineDTO> flightsFrom = ctrl.getFlights(from, to, stringDate, persons);
            return Response.ok(gson.toJson(flightsFrom)).build();
        } finally {
        }
    }
}

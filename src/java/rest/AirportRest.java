package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Airport;
import facades.AirportFacade;
import interfaces.IAirportFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utility.EntityManagerFactoryProvider;

@Path("airports")
public class AirportRest {

    private final IAirportFacade ctrl;
    private Gson gson;
    private EntityManagerFactory emf;

    public AirportRest() {
        emf = EntityManagerFactoryProvider.getEntityManagerFactory();
        ctrl = new AirportFacade(emf);
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAirports() {
        
        List<Airport> airports = ctrl.getAirports();
        return Response.ok(gson.toJson(airports)).build();
        
    }
}

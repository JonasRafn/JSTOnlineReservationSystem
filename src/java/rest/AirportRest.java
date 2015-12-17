package rest;

import entity.Airport;
import facades.AirportFacade;
import interfaces.IAirportFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utility.EntityManagerFactoryProvider;
import static utility.JsonConverter.toJsonAirports;

@Path("airports")
public class AirportRest {

    private final IAirportFacade ctrl;
    private final EntityManagerFactory emf;

    public AirportRest() {
        emf = EntityManagerFactoryProvider.getEntityManagerFactory();
        ctrl = new AirportFacade(emf);
    }

    @GET
    public Response getAirports() {
        List<Airport> airports = ctrl.getAirports();
        return Response.ok(toJsonAirports(airports)).type(MediaType.APPLICATION_JSON).build();
    }
}

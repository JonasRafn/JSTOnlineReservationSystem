package rest;

import dto.AirlineDTO;
import dto.SearchRequestDTO;
import facades.FlightFacade;
import interfaces.IFlightFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utility.EntityManagerFactoryProvider;
import static utility.JsonConverter.toJsonAirlineDTOList;

@Path("flightinfo")
public class FlightInfoRest {

    private final IFlightFacade ctrl;
    private final EntityManagerFactory emf;

    public FlightInfoRest() {
        emf = EntityManagerFactoryProvider.getEntityManagerFactory();
        ctrl = new FlightFacade(emf);
    }

    @GET
    @Path("{from}/{date}/{persons}")
    public Response getFlightsFrom(@PathParam("from") String from, @PathParam("date") String stringDate, @PathParam("persons") String persons) throws Exception {
        SearchRequestDTO request = new SearchRequestDTO(from, stringDate, persons);
        List<AirlineDTO> flightsFrom = ctrl.getFlights(request);
        return Response.ok(toJsonAirlineDTOList(flightsFrom)).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("{from}/{to}/{date}/{persons}")
    public Response getFlightsFromTo(@PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String stringDate, @PathParam("persons") String persons) throws Exception {
        SearchRequestDTO request = new SearchRequestDTO(from, to, stringDate, persons);
        List<AirlineDTO> flightsFrom = ctrl.getFlights(request);
        return Response.ok(toJsonAirlineDTOList(flightsFrom)).type(MediaType.APPLICATION_JSON).build();
    }
}

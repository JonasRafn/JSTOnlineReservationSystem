package facadetest;

import deploy.DeploymentConfiguration;
import dto.AirlineDTO;
import facades.FlightFacade;
import interfaces.IFlightFacade;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FlightFacadeTest {

    public FlightFacadeTest() {
    }

    @Test
    public void getFlightFromSuccess() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        IFlightFacade ctrl = new FlightFacade(emf);
        DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = null;
        try {
            date = sdfISO.parse("2016-01-04T10:00:00.000Z");
        } catch (ParseException ex) {
        }
        List<AirlineDTO> airlines = ctrl.getFlightFrom("CPH", "2016-01-04T10:00:00.000Z", 3);
        assertEquals(3, airlines.size());
        assertTrue(airlines.get(0).getFlights().get(0).getFlightId().equals("COL3256"));
    }
    
    @Test
    public void getFlightFromToSuccess() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        IFlightFacade ctrl = new FlightFacade(emf);
        DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = null;
        try {
            date = sdfISO.parse("2016-01-04T10:00:00.000Z");
        } catch (ParseException ex) {
        }
        List<AirlineDTO> airlines = ctrl.getFlightFromTo("CPH", "SXF", "2016-01-04T10:00:00.000Z", 3);
        assertEquals(3, airlines.size());
    }
}

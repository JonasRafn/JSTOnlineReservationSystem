package facadetest;

import deploy.DeploymentConfiguration;
import entity.Reservation;
import exception.ReservationException;
import exception.ServerException;
import facades.ReservationFacade;
import interfaces.IReservationFacade;
import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReservationFacadeTest {

    private IReservationFacade rf;

    public ReservationFacadeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void resevateTicketsSucces() throws IOException, ServerException, ReservationException {
        rf = new ReservationFacade();
        String reservation = "{\n"
                + " \"flightID\": \"COL3257x100x2016-01-14T21:30:00.000Z\",\n"
                + " \"numberOfSeats\": 2,\n"
                + " \"date\": \"2016-02-25T11:30:00.000Z\",\n"
                + " \"ReserveeName\": \"Peter Hansen\",\n"
                + " \"ReservePhone\": \"12345678\",\n"
                + " \"ReserveeEmail\": \"peter@peter.dk\",\n"
                + " \"Passengers\":[\n"
                + " { \"firstName\":\"Peter\",\n"
                + " \"lastName\": \"Peterson\"\n"
                + " },\n"
                + " { \"firstName\":\"Jane\",\n"
                + " \"lastName\": \"Peterson\"\n"
                + " }\n"
                + " ]\n"
                + "}";

        Reservation res = rf.reservateTickets(reservation, "angular_airline1", "user");

        assertEquals(res.getFlightID(), "COL3257x100x2016-01-14T21:30:00.000Z");
        assertEquals("Peter Hansen", res.getReserveeName());
        assertEquals(90, res.getTraveltime());

    }
}

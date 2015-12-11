package facadetest;

import deploy.DeploymentConfiguration;
import entity.Passenger;
import entity.Reservation;
import entity.User;
import exception.NoResultException;
import exception.ReservationException;
import exception.ServerException;
import facades.ReservationFacade;
import interfaces.IReservationFacade;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
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
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);

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
    public void resevateTicketsTest() throws IOException, ServerException, ReservationException, ParseException {
        EntityManager em = emf.createEntityManager();
        rf = new ReservationFacade();

        Reservation reservation = new Reservation();
        reservation.setAirline("AngularJS Airline-TestAirlineNo: 1");
        reservation.setFlightID("COL3256x100x2016-01-11T10:00:00.000Z");
        reservation.setNumberOfSeats(2);
        DateFormat ISO8601Date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = ISO8601Date.parse("2016-01-11T10:00:00.000Z");

        reservation.setDate(date);
        reservation.setTotalPrice(120);
        reservation.setPricePerson(65);
        reservation.setTraveltime(90);
        reservation.setOrigin("CPH");
        reservation.setDestination("STN");
        reservation.setDestinationCity("London");
        reservation.setDestinationDate(date);
        reservation.setReserveeName("Jonas Rafn");
        reservation.setReserveePhone("+4553555358");
        reservation.setReserveeEmail("jonaschrafn@gmail.com");
        User user = new User("user", "");
        reservation.setUser(user);
        List<Passenger> list = new ArrayList();
        Passenger pass = new Passenger("Jonas", "Rafn");
        list.add(pass);
        reservation.setPassengers(list);

        rf.reserveTickets(reservation);

        Reservation res = em.find(Reservation.class, 3L);

        assertEquals(res.getFlightID(), "COL3256x100x2016-01-11T10:00:00.000Z");
        assertEquals(res.getReserveeName(), "Jonas Rafn");
        assertEquals(res.getTraveltime(), 90);
    }

    @Test
    public void getReservationsTest() throws NoResultException {
        rf = new ReservationFacade();

        String username = "user";
        List<Reservation> list = rf.getReservations(username);

        assertEquals("SK975", list.get(0).getFlightID());
        assertEquals(60, list.get(0).getFlightTime());
    }

}

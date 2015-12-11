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
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ReservationFacadeTest {

    private IReservationFacade ctrl;

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);

    public ReservationFacadeTest() {
        DeploymentConfiguration.setTestModeOn();
    }

    /**
     * Tests user's reservations in DB
     *
     * @throws NoResultException
     * @throws Exception
     */
//    @Test
    public void getReservationsTest() throws NoResultException, Exception {
        ctrl = new ReservationFacade();
        ctrl.deleteReservation(3l);
        String username = "user";
        String airline = "Test Airline 1";
        String flightID = "SK975";
        String reserveePhone = "12345678";
        int travelTime = 60;
        int noOfReservations = 2;
        List<Reservation> list = ctrl.getReservations(username);
        assertEquals(noOfReservations, list.size());
        assertEquals(airline, list.get(0).getAirline());
        assertEquals(airline, list.get(0).getAirline());
        assertEquals(flightID, list.get(0).getFlightID());
        assertEquals(flightID, list.get(1).getFlightID());
        assertEquals(reserveePhone, list.get(0).getReserveePhone());
        assertEquals(reserveePhone, list.get(1).getReserveePhone());
        assertEquals(travelTime, list.get(0).getTraveltime());
        assertEquals(travelTime, list.get(1).getTraveltime());
    }

    /**
     * Tests for NoResultException when trying to get reservations from
     * non-existent user.
     *
     * @throws NoResultException
     * @throws Exception
     */
//    @Test(expected = NoResultException.class)
    public void getReservationUnknownUserTest() throws NoResultException, Exception {
        ctrl = new ReservationFacade();
        String username = "boris";
        List<Reservation> list = ctrl.getReservations(username);
    }

    /**
     * Tests on succesfully created reservation, by creating a
     * reservation-object and persisting to database. Check against persisted
     * values in DB.
     *
     * @throws IOException
     * @throws ServerException
     * @throws ReservationException
     * @throws ParseException
     */
    @Test
    public void reserveTicketsTest() throws IOException, ServerException, ReservationException, ParseException, Exception {
        EntityManager em = emf.createEntityManager();
        ctrl = new ReservationFacade();

        long reservationID = 4l;
        String username = "test";
        String airline = "AngularJS Airline";
        String flightID = "COL3256x100x2016-01-11T10:00:00.000Z";
        int numberOfSeats = 2;
        DateFormat ISO8601Date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dateOrigin = ISO8601Date.parse("2016-01-11T10:00:00.000Z");
        Date dateDestination = ISO8601Date.parse("2016-01-11T11:30:00.000Z");
        float price = 120f;
        float pricePerson = 65f;
        int travelTime = 90;
        String origin = "CPH";
        String originCity = "Copenhagen";
        String destination = "STN";
        String destinationCity = "London";
        String reserveeName = "Jonas";
        String reserveePhone = "+4553555358";
        String reserveeEmail = "jonaschrafn@gmail.com";
        User user = new User(username, "");
        String passengerFirstName1 = "Jonas";
        String passengerLastName1 = "Rafn";
        String passengerFirstName2 = "Lille";
        String passengerLastName2 = "Per";
        Passenger pass1 = new Passenger(passengerFirstName1, passengerLastName1);
        Passenger pass2 = new Passenger(passengerFirstName2, passengerLastName2);
        List<Passenger> list = new ArrayList();
        list.add(pass1);
        list.add(pass2);

        Reservation reservation = new Reservation();
        reservation.setAirline(airline);
        reservation.setFlightID(flightID);
        reservation.setNumberOfSeats(numberOfSeats);
        reservation.setDate(dateOrigin);
        reservation.setTotalPrice(price);
        reservation.setPricePerson(pricePerson);
        reservation.setTraveltime(travelTime);
        reservation.setOrigin(origin);
        reservation.setOriginCity(originCity);
        reservation.setDestination(destination);
        reservation.setDestinationCity(destinationCity);
        reservation.setDestinationDate(dateDestination);
        reservation.setReserveeName(reserveeName);
        reservation.setReserveePhone(reserveePhone);
        reservation.setReserveeEmail(reserveeEmail);
        reservation.setUser(user);
        reservation.setPassengers(list);

        List<Reservation> reservations = ctrl.getReservations(username);
        assertEquals(1, reservations.size());

        ctrl.reserveTickets(reservation);

        reservations = ctrl.getReservations(username);
        assertEquals(2, reservations.size());

        Reservation res = reservations.get(reservations.size() - 1);

        assertEquals(airline, res.getAirline());
        assertEquals(flightID, res.getFlightID());
        assertEquals(numberOfSeats, res.getNumberOfSeats());
        assertEquals(dateOrigin, res.getDate());
        assertEquals(price, res.getTotalPrice(), 0.00);
        assertEquals(pricePerson, res.getPricePerson(), 0.00);
        assertEquals(travelTime, res.getTraveltime());
        assertEquals(origin, res.getOrigin());
        assertEquals(originCity, res.getOriginCity());
        assertEquals(destination, res.getDestination());
        assertEquals(destinationCity, res.getDestinationCity());
        assertEquals(dateDestination, res.getDestinationDate());
        assertEquals(reserveeName, res.getReserveeName());
        assertEquals(reserveePhone, res.getReserveePhone());
        assertEquals(reserveeEmail, res.getReserveeEmail());
        assertEquals(username, res.getUser().getUserName());
        assertEquals(passengerFirstName1, res.getPassengers().get(0).getFirstName());
        assertEquals(passengerLastName1, res.getPassengers().get(0).getLastName());
        assertEquals(passengerFirstName2, res.getPassengers().get(1).getFirstName());
        assertEquals(passengerLastName2, res.getPassengers().get(1).getLastName());

        ctrl.deleteReservation(reservationID);
        reservations = ctrl.getReservations(username);
        assertEquals(1, reservations.size());

    }

    @Test(expected = ReservationException.class)
    public void reserveTicketsNoTicketsAvailableTest() throws IOException, ServerException, ReservationException, Exception {
        ctrl = new ReservationFacade();
        Reservation reservation = new Reservation();
        String username = "admin";
        Passenger pass1 = new Passenger("Silas", "Gnom");
        Passenger pass2 = new Passenger("Gentleman", "Finn");
        reservation.addPassenger(pass1);
        reservation.addPassenger(pass2);
        User user = new User();
        user.setUserName(username);
        String flightID = "COL3256x100x2016-01-01T19:00:00.000Z";
        int numberOfSeats = 1000;
        String reserveeName = "Studievejleder Fyrst Walter";
        String reserveePhone = "12345678";
        String reserveeEmail = "walter@studievejledningen.dk";
        String airline = "AngularJS Airline";
        reservation.setFlightID(flightID);
        reservation.setNumberOfSeats(numberOfSeats);
        reservation.setReserveeName(reserveeName);
        reservation.setReserveePhone(reserveePhone);
        reservation.setReserveeEmail(reserveeEmail);
        reservation.setAirline(airline);
        reservation.setUser(user);
        ctrl.reserveTickets(reservation);
    }

    @Test(expected = ServerException.class)
    public void reserveTicketsUnknownUserTest() throws IOException, ServerException, ReservationException, Exception {
        ctrl = new ReservationFacade();
        Reservation reservation = new Reservation();
        String username = "silas";
        Passenger pass1 = new Passenger("Silas", "Gnom");
        Passenger pass2 = new Passenger("Gentleman", "Finn");
        reservation.addPassenger(pass1);
        reservation.addPassenger(pass2);
        User user = new User();
        user.setUserName(username);
        String flightID = "COL3256x100x2016-01-01T19:00:00.000Z";
        int numberOfSeats = 3;
        String reserveeName = "Studievejleder Fyrst Walter";
        String reserveePhone = "12345678";
        String reserveeEmail = "walter@studievejledningen.dk";
        String airline = "AngularJS Airline";
        reservation.setFlightID(flightID);
        reservation.setNumberOfSeats(numberOfSeats);
        reservation.setReserveeName(reserveeName);
        reservation.setReserveePhone(reserveePhone);
        reservation.setReserveeEmail(reserveeEmail);
        reservation.setAirline(airline);
        reservation.setUser(user);
        ctrl.reserveTickets(reservation);
    }
}

package facadetest;

import deploy.DeploymentConfiguration;
import dto.AirlineDTO;
import exception.BadRequestException;
import exception.NoResultException;
import exception.NotFoundException;
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

/**
 * Tests the implementation of IFlightFacade: FlightFacade
 *
 * Todo: Test for malformed jSon - we need to implement json-schema validation
 * first.
 */
public class FlightFacadeTest {

    public FlightFacadeTest() {
        DeploymentConfiguration.setTestModeOn();
    }

    /**
     * Test on returned dto-data from "getFlights() - [from].
     *
     * @throws java.lang.Exception
     */
    @Test
    public void getFlightFromSuccess() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        IFlightFacade ctrl = new FlightFacade(emf);
        String stringDate = "2016-01-04T15:00:00.000Z";
        String stringDestDate = "2016-01-04T16:00:00.000Z";
        DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = null;
        Date destDate = null;
        try {
            date = sdfISO.parse(stringDate);
            destDate = sdfISO.parse(stringDestDate);
        } catch (ParseException ex) {
        }
        List<AirlineDTO> airlines = ctrl.getFlights("CPH", "", stringDate, 3);
        assertEquals(1, airlines.size());
        assertTrue(airlines.get(0).getAirline().equals("AngularJS Airline-TestAirlineNo: 1"));
        assertTrue(airlines.get(0).getFlights().get(0).getFlightID().equals("COL2216x100x2016-01-04T15:00:00.000Z"));
        assertTrue(airlines.get(0).getFlights().get(0).getNumberOfSeats() == 3);
        assertTrue(airlines.get(0).getFlights().get(0).getDate().equals(date));
        assertTrue(airlines.get(0).getFlights().get(0).getTotalPrice() == 210);
        assertTrue(airlines.get(0).getFlights().get(0).getTraveltime() == 60);
        assertTrue(airlines.get(0).getFlights().get(0).getOrigin().equals("CPH"));
        assertTrue(airlines.get(0).getFlights().get(0).getOriginCity().equals("Copenhagen"));
        assertTrue(airlines.get(0).getFlights().get(0).getDestination().equals("SXF"));
        assertTrue(airlines.get(0).getFlights().get(0).getDestinationCity().equals("Berlin"));
        assertTrue(airlines.get(0).getFlights().get(0).getDestinationDate().equals(destDate));
    }

    /**
     * Test on returned dto-data from "getFlights() - [from+to]
     *
     * @throws Exception
     */
    @Test
    public void getFlightFromToSuccess() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        IFlightFacade ctrl = new FlightFacade(emf);
        String stringDate = "2016-04-01T15:00:00.000Z";
        String stringDestDate = "2016-04-01T16:00:00.000Z";
        DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = null;
        Date destDate = null;
        try {
            date = sdfISO.parse(stringDate);
            destDate = sdfISO.parse(stringDestDate);
        } catch (ParseException ex) {
        }
        List<AirlineDTO> airlines = ctrl.getFlights("CPH", "SXF", stringDate, 3);
        assertEquals(1, airlines.size());
        assertTrue(airlines.get(0).getFlights().get(1).getFlightID().equals("COL2216x100x2016-04-01T15:00:00.000Z"));
        assertTrue(airlines.get(0).getFlights().get(1).getNumberOfSeats() == 3);
        assertEquals(date, airlines.get(0).getFlights().get(1).getDate());
        assertTrue(airlines.get(0).getFlights().get(1).getDate().equals(date));
        assertTrue(airlines.get(0).getFlights().get(1).getTotalPrice() == 210);
        assertTrue(airlines.get(0).getFlights().get(1).getTraveltime() == 60);
        assertTrue(airlines.get(0).getFlights().get(1).getOrigin().equals("CPH"));
        assertTrue(airlines.get(0).getFlights().get(1).getOriginCity().equals("Copenhagen"));
        assertTrue(airlines.get(0).getFlights().get(1).getDestination().equals("SXF"));
        assertTrue(airlines.get(0).getFlights().get(1).getDestinationCity().equals("Berlin"));
        assertEquals(destDate, airlines.get(0).getFlights().get(1).getDestinationDate());
    }

    /**
     * Test NotFoundException from "getFlights()" with unknown airport parameter
     * in [from] and empty [to].
     *
     * @throws Exception
     */
    @Test(expected = NotFoundException.class)
    public void getFlightFromUnknownAirport1() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        IFlightFacade ctrl = new FlightFacade(emf);
        List<AirlineDTO> airlines1 = ctrl.getFlights("BIK", "", "2016-08-01T00:00:00.000Z", 3);
    }

    /**
     * Test NotFoundException from "getFlights()" with known airport parameter
     * in [from] and unknown airport parameter in [to].
     *
     * @throws Exception
     */
    @Test(expected = NotFoundException.class)
    public void getFlightFromToUnknownAirport0() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        IFlightFacade ctrl = new FlightFacade(emf);
        List<AirlineDTO> airlines0 = ctrl.getFlights("CPH", "BIK", "2016-08-01T00:00:00.000Z", 3);
    }

    /**
     * Test NotFoundException from "getFlights()" with unknown airport parameter
     * in [from] and known in [to].
     *
     * @throws Exception
     */
    @Test(expected = NotFoundException.class)
    public void getFlightFromToUnknownAirport1() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        IFlightFacade ctrl = new FlightFacade(emf);
        List<AirlineDTO> airlines1 = ctrl.getFlights("BIK", "CPH", "2016-08-01T00:00:00.000Z", 3);
    }

    /**
     * Test NotFoundException from "getFlights()" with empty airport parameters
     * in [from] and [to].
     *
     * @throws Exception
     */
    @Test(expected = NotFoundException.class)
    public void getFlightFromToUnknownAirport2() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        IFlightFacade ctrl = new FlightFacade(emf);
        List<AirlineDTO> airlines2 = ctrl.getFlights("", "", "2016-08-01T00:00:00.000Z", 3);
    }

    /**
     * Test NotFoundException from "getFlights()" with unknown airport parameter
     * in [from] and empty in [to].
     *
     * @throws Exception
     */
    @Test(expected = NotFoundException.class)
    public void getFlightFromToUnknownAirport3() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        IFlightFacade ctrl = new FlightFacade(emf);
        List<AirlineDTO> airlines3 = ctrl.getFlights("BIK", "", "2016-08-01T00:00:00.000Z", 3);
    }

    /**
     * Test NotFoundException from "getFlights()" with empty airport parameter
     * in [from] and unknown in [to].
     *
     * @throws Exception
     */
    @Test(expected = NotFoundException.class)
    public void getFlightFromToUnknownAirport4() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        IFlightFacade ctrl = new FlightFacade(emf);
        List<AirlineDTO> airlines4 = ctrl.getFlights("", "BIK", "2016-08-01T00:00:00.000Z", 3);
    }

    /**
     * Test BadRequestException from "getFlights()" with empty date parameter.
     *
     * @throws Exception
     */
    @Test(expected = BadRequestException.class)
    public void getFlightFromBadDate0() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        IFlightFacade ctrl = new FlightFacade(emf);
        List<AirlineDTO> airlines0 = ctrl.getFlights("CPH", "", "", 3);
    }

    /**
     * Test BadRequestException from "getFlights()" with invalid formatted date
     * parameter, variant 1.
     *
     * @throws Exception
     */
    @Test(expected = BadRequestException.class)
    public void getFlightFromBadDate1() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        IFlightFacade ctrl = new FlightFacade(emf);
        List<AirlineDTO> airlines1 = ctrl.getFlights("CPH", "", "invalid date", 3);
    }

    /**
     * Test BadRequestException from "getFlights()" with invalid formatted date
     * parameter, variant 2.
     *
     * @throws Exception
     */
    @Test(expected = BadRequestException.class)
    public void getFlightFromBadDate2() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        IFlightFacade ctrl = new FlightFacade(emf);
        List<AirlineDTO> airlines2 = ctrl.getFlights("SXF", "", "2016-04-01T06:00:00.000", 3);
    }

    /**
     * Test BadRequestException from "getFlights()" with invalid formatted date
     * parameter, variant 3.
     *
     * @throws Exception
     */
    @Test(expected = BadRequestException.class)
    public void getFlightFromBadDate3() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        IFlightFacade ctrl = new FlightFacade(emf);
        List<AirlineDTO> airlines3 = ctrl.getFlights("CDG", "", "01-01-2016", 3);
    }

    /**
     * Test BadRequestException from "getFlights()" with invalid formatted date
     * parameter, variant 3 with both [from] and [to] parameters.
     *
     * @throws Exception
     */
    @Test(expected = BadRequestException.class)
    public void getFlightFromToBadDate() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        IFlightFacade ctrl = new FlightFacade(emf);
        List<AirlineDTO> airlines4 = ctrl.getFlights("CDG", "CPH", "01-01-2016", 3);
    }

    /**
     * Test NoResultException from "getFlights()" when no flights are available
     * with [from]. No tickets available.
     *
     * @throws Exception
     */
    @Test(expected = NoResultException.class)
    public void getFlightFromNoFlights() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        IFlightFacade ctrl = new FlightFacade(emf);
        List<AirlineDTO> airlines0 = ctrl.getFlights("CPH", "", "2016-10-01T00:00:00.000Z", 100);
    }

    /**
     * Test NoResultException from "getFlights()" when no flights are available
     * with [from] and [to] parameters. No flights available.
     *
     * @throws Exception
     */
    @Test(expected = NoResultException.class)
    public void getFlightFromToNoFlights() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        IFlightFacade ctrl = new FlightFacade(emf);
        List<AirlineDTO> airlines0 = ctrl.getFlights("CPH", "BCN", "2016-12-15T00:00:00.000Z", 10);
    }
}

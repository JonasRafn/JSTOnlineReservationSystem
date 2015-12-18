package facadetest;

import deploy.DeploymentConfiguration;
import dto.AirlineDTO;
import entity.SearchRequest;
import exception.NoResultException;
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
        String stringDate = "2016-04-01T06:00:00.000Z";
        SearchRequest request = new SearchRequest("CPH", "", stringDate, 3);
        String stringDestDate = "2016-04-01T07:00:00.000Z";
        DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = null;
        Date destDate = null;
        try {
            date = sdfISO.parse(stringDate);
            destDate = sdfISO.parse(stringDestDate);
        } catch (ParseException ex) {
        }
        List<AirlineDTO> airlines = ctrl.getFlights(request);
        assertEquals(1, airlines.size());
        assertTrue(airlines.get(0).getAirline().equals("AngularJS Airline-TestAirlineNo: 1"));
        assertEquals("COL2214", airlines.get(0).getFlights().get(0).getFlightID());
        assertTrue(airlines.get(0).getFlights().get(0).getNumberOfSeats() == 3);
        assertEquals(date, airlines.get(0).getFlights().get(0).getStringDate());
        assertTrue(airlines.get(0).getFlights().get(0).getTotalPrice() == 225);
        assertTrue(airlines.get(0).getFlights().get(0).getTraveltime() == 60);
        assertTrue(airlines.get(0).getFlights().get(0).getOrigin().equals("CPH"));
        assertTrue(airlines.get(0).getFlights().get(0).getOriginCity().equals("Copenhagen"));
        assertTrue(airlines.get(0).getFlights().get(0).getDestination().equals("SXF"));
        assertTrue(airlines.get(0).getFlights().get(0).getDestinationCity().equals("Berlin"));
        assertEquals(destDate, airlines.get(0).getFlights().get(0).getStringDestinationDate());
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
        SearchRequest request = new SearchRequest("CPH", "SXF", stringDate, 3);
        String stringDestDate = "2016-04-01T16:00:00.000Z";
        DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = null;
        Date destDate = null;
        try {
            date = sdfISO.parse(stringDate);
            destDate = sdfISO.parse(stringDestDate);
        } catch (ParseException ex) {
        }
        List<AirlineDTO> airlines = ctrl.getFlights(request);
        assertEquals(1, airlines.size());
        assertEquals("COL2216", airlines.get(0).getFlights().get(1).getFlightID());
        assertTrue(airlines.get(0).getFlights().get(1).getNumberOfSeats() == 3);
        assertEquals(date, airlines.get(0).getFlights().get(1).getStringDate());
        assertTrue(airlines.get(0).getFlights().get(1).getStringDate().equals(date));
        assertTrue(airlines.get(0).getFlights().get(1).getTotalPrice() == 210);
        assertTrue(airlines.get(0).getFlights().get(1).getTraveltime() == 60);
        assertTrue(airlines.get(0).getFlights().get(1).getOrigin().equals("CPH"));
        assertTrue(airlines.get(0).getFlights().get(1).getOriginCity().equals("Copenhagen"));
        assertTrue(airlines.get(0).getFlights().get(1).getDestination().equals("SXF"));
        assertTrue(airlines.get(0).getFlights().get(1).getDestinationCity().equals("Berlin"));
        assertEquals(destDate, airlines.get(0).getFlights().get(1).getStringDestinationDate());
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
        SearchRequest request = new SearchRequest("BIK", "", "2016-08-01T00:00:00.000Z", 3);
        List<AirlineDTO> airlines1 = ctrl.getFlights(request);
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
        SearchRequest request = new SearchRequest("CPH", "BIK", "2016-08-01T00:00:00.000Z", 3);
        List<AirlineDTO> airlines0 = ctrl.getFlights(request);
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
        SearchRequest request = new SearchRequest("BIK", "CPH", "2016-08-01T00:00:00.000Z", 3);
        List<AirlineDTO> airlines1 = ctrl.getFlights(request);
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
        SearchRequest request = new SearchRequest("", "", "2016-08-01T00:00:00.000Z", 3);
        List<AirlineDTO> airlines2 = ctrl.getFlights(request);
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
        SearchRequest request = new SearchRequest("BIK", "", "2016-08-01T00:00:00.000Z", 3);
        List<AirlineDTO> airlines3 = ctrl.getFlights(request);
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
        SearchRequest request = new SearchRequest("", "BIK", "2016-08-01T00:00:00.000Z", 3);
        List<AirlineDTO> airlines4 = ctrl.getFlights(request);
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
        SearchRequest request = new SearchRequest("CPH", "", "", 3);
        List<AirlineDTO> airlines0 = ctrl.getFlights(request);
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
        SearchRequest request = new SearchRequest("CPH", "", "invalid date", 3);
        List<AirlineDTO> airlines1 = ctrl.getFlights(request);
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
        SearchRequest request = new SearchRequest("SXF", "", "2016-04-01T06:00:00.000", 3);
        List<AirlineDTO> airlines2 = ctrl.getFlights(request);
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
        SearchRequest request = new SearchRequest("CDG", "", "01-01-2016", 3);
        List<AirlineDTO> airlines3 = ctrl.getFlights(request);
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
        SearchRequest request = new SearchRequest("CDG", "CPH", "01-01-2016", 3);
        List<AirlineDTO> airlines4 = ctrl.getFlights(request);
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
        SearchRequest request = new SearchRequest("CPH", "", "2016-19-01T00:00:00.000Z", 100);
        List<AirlineDTO> airlines0 = ctrl.getFlights(request);
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
        SearchRequest request = new SearchRequest("CPH", "BCN", "2016-12-15T00:00:00.000Z", 10);
        List<AirlineDTO> airlines0 = ctrl.getFlights(request);
    }
}

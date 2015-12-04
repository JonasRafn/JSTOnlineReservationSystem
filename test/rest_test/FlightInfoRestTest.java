package rest_test;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.defaultParser;
import static com.jayway.restassured.RestAssured.when;
import com.jayway.restassured.parsing.Parser;
import deploy.DeploymentConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import static org.hamcrest.Matchers.equalTo;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.ApplicationConfig;

public class FlightInfoRestTest {

    static Server server;

    public FlightInfoRestTest() {
        DeploymentConfiguration.setTestModeOn();
        baseURI = "http://localhost:9090";
        defaultParser = Parser.JSON;
        basePath = "/api/flightinfo";
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        server = new Server(9090);
        ServletHolder servletHolder = new ServletHolder(org.glassfish.jersey.servlet.ServletContainer.class);
        servletHolder.setInitParameter("javax.ws.rs.Application", ApplicationConfig.class.getName());
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        contextHandler.addServlet(servletHolder, "/api/*");
        server.setHandler(contextHandler);
        server.start();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        server.stop();
        //waiting for all the server threads to terminate so we can exit gracefully
        server.join();
    }

    /**
     * Test get with successful json-response
     */
    @Test
    public void test200_ok() {
        when().
                get("/CPH/SXF/2016-01-01T00:00:00.000Z/3").
                then().
                statusCode(200).
                body("[0].size()", equalTo(2)).
                body("[0].airline", equalTo("AngularJS Airline-TestAirlineNo: 1")).
                body("[0].flights[0].flightID", equalTo("COL2216x100x2016-01-01T15:00:00.000Z")).
                body("[0].flights[0].numberOfSeats", equalTo(3)).
                body("[0].flights[0].date", equalTo("2016-01-01T15:00:00.000Z")).
                body("[0].flights[0].totalPrice", equalTo(210f)).
                body("[0].flights[0].traveltime", equalTo(60)).
                body("[0].flights[0].origin", equalTo("CPH")).
                body("[0].flights[0].originCity", equalTo("Copenhagen")).
                body("[0].flights[0].destination", equalTo("SXF")).
                body("[0].flights[0].destinationCity", equalTo("Berlin")).
                body("[0].flights[0].destinationDate", equalTo("2016-01-01T16:00:00.000Z")).
                body("[0].flights[1].flightID", equalTo("COL2214x100x2016-01-01T06:00:00.000Z")).
                body("[0].flights[1].numberOfSeats", equalTo(3)).
                body("[0].flights[1].date", equalTo("2016-01-01T06:00:00.000Z")).
                body("[0].flights[1].totalPrice", equalTo(225f)).
                body("[0].flights[1].traveltime", equalTo(60)).
                body("[0].flights[1].origin", equalTo("CPH")).
                body("[0].flights[1].originCity", equalTo("Copenhagen")).
                body("[0].flights[1].destination", equalTo("SXF")).
                body("[0].flights[1].destinationCity", equalTo("Berlin")).
                body("[0].flights[1].destinationDate", equalTo("2016-01-01T07:00:00.000Z"));

        when().
                get("/CPH/SXF/2016-06-15T00:00:00.000Z/4").
                then().
                statusCode(200).
                body("size()", equalTo(1)).
                body("[0].airline", equalTo("AngularJS Airline-TestAirlineNo: 1")).
                body("[0].flights[0].flightID", equalTo("COL2214x100x2016-06-15T06:00:00.000Z")).
                body("[0].flights[0].numberOfSeats", equalTo(4)).
                body("[0].flights[0].date", equalTo("2016-06-15T06:00:00.000Z")).
                body("[0].flights[0].totalPrice", equalTo(300f)).
                body("[0].flights[0].traveltime", equalTo(60)).
                body("[0].flights[0].origin", equalTo("CPH")).
                body("[0].flights[0].originCity", equalTo("Copenhagen")).
                body("[0].flights[0].destination", equalTo("SXF")).
                body("[0].flights[0].destinationCity", equalTo("Berlin")).
                body("[0].flights[0].destinationDate", equalTo("2016-06-15T07:00:00.000Z"));
    }

    /**
     * Test get with no available flights
     */
    @Test
    public void test204_no_content() {
        when().
                get("/CPH/2016-06-15T00:00:00.000Z/100").
                then().
                statusCode(204);
        when().
                get("/CPH/STN/2016-06-15T00:00:00.000Z/5").
                then().
                statusCode(204);
    }

    /**
     * Test get with unknown airport
     */
    @Test
    public void test404_not_found() {
        when().
                get("/BIK/2016-06-15T00:00:00.000Z/5").
                then().
                statusCode(404).
                body("size()", equalTo(1)).
                body("message", equalTo("Unknown origin airport: BIK"));
        when().
                get("/CPH/BIK/2016-06-15T00:00:00.000Z/5").
                then().
                statusCode(404).
                body("size()", equalTo(1)).
                body("message", equalTo("Unknown destination airport: BIK"));
        when().
                get("/BIK/BIK/2016-06-15T00:00:00.000Z/5").
                then().
                statusCode(404).
                body("size()", equalTo(1)).
                body("message", equalTo("Unknown destination airport: BIK"));
    }

    /**
     * Test get with invalid formatted date
     */
    @Test
    public void test400_bad_request() {
        when().
                get("/CPH/SXF/2016-01-01T00:00:00.000/3").
                then().
                statusCode(400).
                body("size()", equalTo(1)).
                body("message", equalTo("Invalid date"));
        when().
                get("/CPH/SXF/invalid date/3").
                then().
                statusCode(400).
                body("size()", equalTo(1)).
                body("message", equalTo("Invalid date"));
    }
}

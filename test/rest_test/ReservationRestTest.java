package rest_test;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.defaultParser;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.parsing.Parser;
import static com.jayway.restassured.path.json.JsonPath.from;
import deploy.DeploymentConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import static org.hamcrest.Matchers.equalTo;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.ApplicationConfig;

public class ReservationRestTest {

    static Server server;

    static String request = "{\"airline\":\"AngularJS Airline\","
            + "\"flightID\":\"COL2334x100x2016-01-16T16:00:00.000Z\","
            + "\"numberOfSeats\":\"2\","
            + "\"date\":\"2016-01-16T16:00:00.000Z\","
            + "\"totalPrice\":240,"
            + "\"pricePerson\":120,"
            + "\"flightTime\":180,"
            + "\"origin\":\"CPH\","
            + "\"originCity\":\"Copenhagen\","
            + "\"destination\":\"BCN\","
            + "\"destinationCity\":\"Barcelona\","
            + "\"destinationDate\":\"2016-01-16T19:00:00.000Z\","
            + "\"user\":{\"userName\":\"user\"},"
            + "\"passengers\":"
            + "["
            + "{\"firstName\":\"Lars\",\"lastName\":\"Krimi\"},"
            + "{\"firstName\":\"Fyrst\",\"lastName\":\"Walter\"}"
            + "],"
            + "\"reserveeName\":\"Lars Krimi\","
            + "\"reserveePhone\":\"12345678\","
            + "\"reserveeEmail\":\"lars@krimi.dk\"}";

    public ReservationRestTest() {
        DeploymentConfiguration.setTestModeOn();
        baseURI = "http://localhost:8082";
        defaultParser = Parser.JSON;
        basePath = "/api";
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        server = new Server(8082);
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
     * Tests successful reservation of tickets
     */
    @Test
    public void reserveTicketAuthorizedTest() {
        //First, make a login to get the token for the Authorization, saving the response body in String json
        String json = given().
                contentType("application/json").
                body("{'username':'user','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();
        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                body(request).
                post("/reservation").
                then().
                statusCode(200).
                body("message", equalTo("Tickets succesfully reserved"));
        json = given().
                contentType("application/json").
                body("{'username':'admin','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();
        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                body(request).
                delete("/reservation/4").
                then().
                statusCode(200).
                body("message", equalTo("Reservation succesfully removed"));

    }

    @Test
    public void reserveTicketsNotAuthorizedTest() {
        //No login first, so the rest will return 401 Not Authorized
        given().
                contentType("application/json").
                when().
                body(request).
                post("/reservation").
                then().
                statusCode(401);
    }

    @Test
    public void getUserReservationsAuthorizedTest() {
        String user = "user";
        //First, make a login to get the token for the Authorization, saving the response body in String json
        String json = given().
                contentType("application/json").
                body("{'username':'user','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();
        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/reservation/" + user).
                then().
                statusCode(200).
                body("[0].flightID", equalTo("SK975")).
                body("[0].reserveePhone", equalTo("12345678"));
    }

    @Test
    public void getUserReservationsNotAuthorizedTest() {
        String user = "user";
        //No login first, so the rest will return 401 Not Authorized
        given().
                contentType("application/json").
                when().
                get("/reservation/" + user).
                then().
                statusCode(401);
    }

    @Test
    public void getAllReservationsAuthorizedTest() {
        String user = "admin";
        //First, make a login to get the token for the Authorization, saving the response body in String json
        String json = given().
                contentType("application/json").
                body("{'username':'user','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();
        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/reservation/" + user).
                then().
                statusCode(200).
                body("size()", equalTo(3)).
                body("[0].flightID", equalTo("SK975")).
                body("[1].flightID", equalTo("SK975")).
                body("[2].flightID", equalTo("SK800"));
    }
}

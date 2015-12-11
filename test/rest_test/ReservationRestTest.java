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

    static String request = "{\n"
            + "    \"airline\": \"AngularJS Airline-TestAirlineNo: 1\",\n"
            + "    \"flightID\": \"COL3256x100x2016-01-11T10:00:00.000Z\",\n"
            + "    \"date\": \"2016-01-11T10:00:00.000Z\",\n"
            + "    \"totalPrice\": 130,\n"
            + "    \"pricePerson\": 65,\n"
            + "    \"flightTime\": 90,\n"
            + "    \"origin\": \"CPH\",\n"
            + "    \"originCity\": \"Copenhagen\",\n"
            + "    \"destination\": \"STN\",\n"
            + "    \"destinationCity\": \"London\",\n"
            + "    \"destinationDate\": \"2016-01-11T10:30:00.000Z\",\n"
            + "    \"user\": {\n"
            + "        \"userName\": \"user\"\n"
            + "    },\n"
            + "    \"passengers\": [\n"
            + "        {\n"
            + "            \"firstName\": \"Jonas\",\n"
            + "            \"lastName\": \"Rafn\"\n"
            + "        }\n"
            + "    ],\n"
            + "    \"numberOfSeats\": \"3\",\n"
            + "    \"reserveeName\": \"Jonas Rafn\",\n"
            + "    \"reserveePhone\": \"+4553555358\",\n"
            + "    \"reserveeEmail\": \"jonaschrafn@gmail.com\"\n"
            + "}";

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
                statusCode(200);
    }

//    @Test
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

//    @Test
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
        given(). //ReservationRest @RolesAllowed("User")
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/reservation/" + user).
                then().
                statusCode(200).
                body("[0].flightID", equalTo("SK975"));
    }

//    @Test
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

//    @Test
    public void getAllReservationsAuthorizedTest() {
        //First, make a login to get the token for the Authorization, saving the response body in String json
        String json = given().
                contentType("application/json").
                body("{'username':'user','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();
        given(). //ReservationRest @RolesAllowed("User")
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/reservation/all").
                then().
                statusCode(200).
                body("[0].flightID", equalTo("SK975"));
    }

//    @Test
    public void getAllReservationsNotAuthorizedTest() {
        //No login first, so the rest will return 401 Not Authorized
        given().
                contentType("application/json").
                when().
                get("/reservation/all").
                then().
                statusCode(401);
    }
}

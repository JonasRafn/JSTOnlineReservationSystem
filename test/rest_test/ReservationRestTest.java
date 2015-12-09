package rest_test;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.defaultParser;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.parsing.Parser;
import entity.User;
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

    public ReservationRestTest() {
//        DeploymentConfiguration.setTestModeOn();
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

    @Test
    public void reservateTicketSuccess() {
        String request = "{\n"
                + " \"flightID\": \"COL3257x100x2016-01-14T21:30:00.000Z\",\n"
                + " \"numberOfSeats\": 2,\n"
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

        String groupName = "angular_airline1";
        String user = "user";

        //Reservate tickets
        given().contentType("application/json").
                body(request).
                when().
                post("/reservation/" + groupName + "/" + user).
                then().
                statusCode(200).
                body("message", equalTo("Tickets succesfully reserved"));
    }

//    @Test
    public void createExistingUser() {
        User user1 = new User("kurt", "1234");
        //Create a new user 
        given().contentType("application/json").
                body(user1).
                when().
                post("/create").
                then().
                statusCode(201).
                body("userName", equalTo("kurt"));
//                body("roles", hasItems("User"));
        //Try to create the same user again
        User user2 = new User("kurt", "1234");
        given().contentType("application/json").
                body(user2).
                when().
                post("/create").
                then().
                statusCode(409);
    }

//    @Test
    public void createEmptyUser() {
        User user1 = new User("", "");
        //Create a new empty user 
        given().contentType("application/json").
                body(user1).
                when().
                post("/create").
                then().
                statusCode(400);
    }
}

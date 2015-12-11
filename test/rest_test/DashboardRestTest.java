/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest_test;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.defaultParser;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import com.jayway.restassured.parsing.Parser;
import static com.jayway.restassured.path.json.JsonPath.from;
import deploy.DeploymentConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.ApplicationConfig;
import static rest_test.ReservationRestTest.request;

/**
 *
 * @author sebastiannielsen
 */
public class DashboardRestTest {

    static Server server;

    public DashboardRestTest() {
        DeploymentConfiguration.setTestModeOn();
        baseURI = "http://localhost:8082";
        defaultParser = Parser.JSON;
        basePath = "/api/dashboard";

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
        server.join();
    }

    @Test
    public void test200_ok() {
//        String json = given().
//                contentType("application/json").
//                body("{'username':'user','password':'test'}").
//                when().
//                post("/login").
//                then().
//                statusCode(200).extract().asString();
//        given(). //ReservationRest @RolesAllowed("User")
//                contentType("application/json").
//                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/history").
                then().
                statusCode(200)
                .body("numberOfSearches", equalTo(4))
                .body("averageNumberOfTickets", is(2.0f))
                .body("numberOfAirlines", equalTo(3))
                .body("numberOfReservations", equalTo(3))
                .body("mostPopularDestinations.size()", equalTo(1))
                .body("mostPopularMonths.size()", equalTo(1));

    }

}

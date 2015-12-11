package rest_test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({rest_test.ReservationRestTest.class, rest_test.FlightInfoRestTest.class, rest_test.CreateUserRestTest.class, rest_test.DashboardRestTest.class})
public class RestSuite {
    
}

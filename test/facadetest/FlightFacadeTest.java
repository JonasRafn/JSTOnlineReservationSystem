package facadetest;

import dto.FlightDTO;
import facades.FlightFacade;
import interfaces.IFlightFacade;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class FlightFacadeTest {

    public FlightFacadeTest() {
    }

    @Test
    public void getFlightFrom() {
        IFlightFacade ctrl = new FlightFacade();
        DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = null;
        try {
            date = sdfISO.parse("2016-01-04T10:00:00.000Z");
        } catch (ParseException ex) {
        }
        List<FlightDTO> list = ctrl.getFlightFrom("CPH", "2016-01-04T10:00:00.000Z", 3);
        assertTrue(list.get(0).getFlightId().equals("COL3256"));
        assertTrue(list.get(1).getFlightId().equals("COL3256"));
        assertTrue(list.get(2).getFlightId().equals("COL2214"));
        assertTrue(list.get(3).getFlightId().equals("COL2216"));
    }

}

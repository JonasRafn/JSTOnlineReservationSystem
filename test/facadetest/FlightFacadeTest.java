package facadetest;

import dto.FlightDTO;
import facades.FlightFacade;
import interfaces.IFlightFacade;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    }

}

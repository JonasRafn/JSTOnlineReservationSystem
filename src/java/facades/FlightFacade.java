package facades;

import dto.FlightDTO;
import interfaces.IFlightFacade;
import java.util.List;

public class FlightFacade implements IFlightFacade {

    @Override
    public List<FlightDTO> getFlightFrom(String from, String date, int numTickets) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

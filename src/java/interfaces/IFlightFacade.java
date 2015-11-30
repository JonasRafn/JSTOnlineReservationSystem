package interfaces;

import dto.FlightDTO;
import java.util.List;

public interface IFlightFacade {
    public List<FlightDTO> getFlightFrom(String from, String date, int numTickets);
}



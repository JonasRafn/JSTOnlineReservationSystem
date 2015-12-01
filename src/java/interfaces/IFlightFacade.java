package interfaces;

import dto.AirlineDTO;
import java.util.List;

public interface IFlightFacade {

    public List<AirlineDTO> getFlightFrom(String from, String date, int numTickets);
}

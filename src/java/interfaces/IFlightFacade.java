package interfaces;

import dto.AirlineDTO;
import exception.BadRequestException;
import java.util.List;

public interface IFlightFacade {

    public List<AirlineDTO> getFlights(String from, String to, String date, int numTickets) throws BadRequestException;

}

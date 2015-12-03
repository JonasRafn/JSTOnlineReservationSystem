package interfaces;

import dto.AirlineDTO;
import exception.BadRequestException;
import exception.NoResultException;
import exception.NotFoundException;
import exception.ServerException;
import java.util.List;

public interface IFlightFacade {

    public List<AirlineDTO> getFlights(String from, String to, String date, int numTickets)
            throws NotFoundException, NoResultException, BadRequestException, ServerException;
}

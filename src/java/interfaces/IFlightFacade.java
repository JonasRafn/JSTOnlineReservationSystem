package interfaces;

import dto.AirlineDTO;
import exception.BadRequestException;
import exception.NoResultException;
import exception.NotFoundException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface IFlightFacade {

    public List<AirlineDTO> getFlights(String from, String to, String date, int numTickets) throws NotFoundException, NoResultException, BadRequestException;
    
//    public Date calculateLocalTime(String originTZ, String destinationTZ, Date date) throws ParseException;

}

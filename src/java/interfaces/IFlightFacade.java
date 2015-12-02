package interfaces;

import dto.AirlineDTO;
import exception.BadRequestException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface IFlightFacade {

    public List<AirlineDTO> getFlights(String from, String to, String date, int numTickets) throws BadRequestException;
    
    public Date calculateLocalTime(String originTZ, String destinationTZ, Date date) throws ParseException;

}

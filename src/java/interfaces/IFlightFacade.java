package interfaces;

import dto.AirlineDTO;
import dto.SearchRequestDTO;
import exception.BadRequestException;
import exception.NoResultException;
import exception.NotFoundException;
import exception.ServerException;
import java.util.List;

public interface IFlightFacade {

//    /**
//     * get all available flights from all airlines
//     *
//     * @param from from airport
//     * @param to to airport
//     * @param date travel date
//     * @param numTickets number of tickets
//     * @return
//     * @throws NotFoundException airport from or to not found
//     * @throws NoResultException no available flights
//     * @throws BadRequestException invalid dates
//     * @throws ServerException internal server error
//     */
//    public List<AirlineDTO> getFlights(String from, String to, String date, int numTickets)
//            throws NotFoundException, NoResultException, BadRequestException, ServerException;
    
    /**
     * get all available flights from all airlines
     *
     * @param request object containing the users search param
     * @return
     * @throws NotFoundException airport from or to not found
     * @throws NoResultException no available flights
     * @throws BadRequestException invalid dates
     * @throws ServerException internal server error
     */
    public List<AirlineDTO> getFlights(SearchRequestDTO request)
            throws NotFoundException, NoResultException, BadRequestException, ServerException, Exception;
}

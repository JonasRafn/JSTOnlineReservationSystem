package interfaces;

import dto.AirlineDTO;
import dto.SearchRequestDTO;
import java.util.List;

public interface IFlightFacade {

    /**
     * get all available flights from all airlines
     *
     * @param request object containing the users search param
     * @return
     * @throws java.lang.Exception
     */
    public List<AirlineDTO> getFlights(SearchRequestDTO request) throws Exception;
}

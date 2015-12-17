package dto;

import java.util.ArrayList;
import java.util.List;

public class AirlineDTO {

    private String airline;
    private List<FlightDTO> flights = new ArrayList();

    public AirlineDTO() {
    }
    
    public AirlineDTO(String airline) {
        this.airline = airline;
        flights = new ArrayList();
    }

    public List<FlightDTO> getFlights() {
        return flights;
    }

    public void addFlights(FlightDTO dto) {
        flights.add(dto);
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }
}

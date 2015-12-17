package utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.AirlineDTO;
import dto.HistoryDTO;
import dto.ReservationDTO;
import entity.Airport;
import entity.Reservation;
import java.util.ArrayList;
import java.util.List;

public class JsonConverter {
    
    private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();

    public static String toJsonReservationList(List<Reservation> list) {
        List<ReservationDTO> dtoList = new ArrayList();
        for (Reservation r : list) {
            ReservationDTO dto = new ReservationDTO(r.getId(), r.getAirline(), r.getFlightID(), r.getNumberOfSeats(), r.getDate(), r.getTotalPrice(), r.getPricePerson(), r.getFlightTime(), r.getOrigin(), r.getOriginCity(), r.getDestination(), r.getDestinationCity(), r.getDestinationDate(), r.getReserveeName(), r.getReserveePhone(), r.getReserveeEmail(), r.getPassengers(), r.getUser().getUserName());
            dtoList.add(dto);
        }
        return GSON.toJson(dtoList);
    }
    
    public static String toJsonAirlineDTOList(List<AirlineDTO> flightsFrom) {
        return GSON.toJson(flightsFrom);
    }
    
    public static String toJsonAirports(List<Airport> airports) {
        return GSON.toJson(airports);
    }
    
    public static String toJsonHistoryDTO(HistoryDTO dto) {
        return GSON.toJson(dto);
    }
    
    public static Reservation fromJsonReservation(String reservation) {
        return GSON.fromJson(reservation, Reservation.class);
    }
    
    public static entity.User fromJsonUser(String user) {
        return GSON.fromJson(user, entity.User.class);
    }
    
    
    
    
    
    
}

package utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dto.ReservationDTO;
import entity.Reservation;
import java.util.ArrayList;
import java.util.List;

public class JsonConverter {
    
    private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();

    public static String toJson(List<Reservation> list) {
        List<ReservationDTO> dtoList = new ArrayList();
        for (Reservation r : list) {
            ReservationDTO dto = new ReservationDTO(r.getAirline(), r.getFlightID(), r.getNumberOfSeats(), r.getDate(), r.getTotalPrice(), r.getPricePerson(), r.getFlightTime(), r.getOrigin(), r.getOriginCity(), r.getDestination(), r.getDestinationCity(), r.getDestinationDate(), r.getReserveeName(), r.getReserveePhone(), r.getReserveeEmail(), r.getPassengers());
            dtoList.add(dto);
        }
        return GSON.toJson(dtoList);
    }
}

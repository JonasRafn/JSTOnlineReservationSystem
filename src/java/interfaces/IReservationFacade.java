package interfaces;

import entity.Reservation;
import java.util.List;

public interface IReservationFacade {
    
    public List<Reservation> getReservations(String username);
    
}

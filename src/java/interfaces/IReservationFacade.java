package interfaces;

import entity.Reservation;
import java.util.List;

public interface IReservationFacade {

    public List<Reservation> getReservations(String username) throws Exception;
    
    public void reserveTickets(Reservation reservation) throws Exception;
    
    public void deleteReservation(long reservationID) throws Exception;

}

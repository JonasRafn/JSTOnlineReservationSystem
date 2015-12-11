package interfaces;

import entity.Reservation;
import exception.NoResultException;
import exception.NotFoundException;
import exception.ReservationException;
import exception.ServerException;
import java.io.IOException;
import java.util.List;

public interface IReservationFacade {

    public List<Reservation> getReservations(String username) throws NoResultException, Exception;
    
    public void reserveTickets(Reservation reservation) throws IOException, ServerException, ReservationException, Exception;
    
    public void deleteReservation(long reservationID) throws NotFoundException, Exception;

}

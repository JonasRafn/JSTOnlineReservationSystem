package interfaces;

import entity.Reservation;
import exception.NoResultException;
import exception.ReservationException;
import exception.ServerException;
import java.io.IOException;
import java.util.List;

public interface IReservationFacade {

    public List<Reservation> getReservations(String username) throws NoResultException;

    public Reservation reservateTickets(String reservation, String groupName, String user) throws IOException, ServerException, ReservationException;

}

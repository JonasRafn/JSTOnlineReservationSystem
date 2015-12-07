package facades;

import interfaces.IReservationFacade;
import entity.Reservation;
import java.util.List;
import javax.persistence.EntityManagerFactory;

public class ReservationFacade implements IReservationFacade {
    private EntityManagerFactory emf;
    
    public ReservationFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    @Override
    public List<Reservation> getReservations(String username) {
        return null;
    }
    
}

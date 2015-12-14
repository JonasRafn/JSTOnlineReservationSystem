package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import deploy.DeploymentConfiguration;
import dto.PassengerDTO;
import dto.ReservationRequestDTO;
import entity.AirlineApi;
import entity.Passenger;
import interfaces.IReservationFacade;
import entity.Reservation;
import entity.User;
import exception.NoResultException;
import exception.NotFoundException;
import exception.ReservationException;
import exception.ServerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ReservationFacade implements IReservationFacade {

    private final EntityManagerFactory emf;
    private final Gson gson;

    public ReservationFacade() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
        this.emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    }

    @Override
    public List<Reservation> getReservations(String username) throws NoResultException, Exception {
        EntityManager em = getEntityManager();
        List<Reservation> reservations = new ArrayList();
        try {
            TypedQuery<Reservation> query;
            if (username.equals("admin")) {
                query = em.createNamedQuery("Reservation.findAll", Reservation.class);
            } else {
                User user = new User();
                user.setUserName(username);
                query = em.createNamedQuery("Reservation.findAllbyUser", Reservation.class).setParameter("user", user);
            }
            reservations = query.getResultList();
            if (reservations.isEmpty()) {
                throw new NoResultException();
            }
        } finally {
            em.close();
        }
        return reservations;
    }

    /**
     * Post a reservationRequest to a airline based on the groupName
     *
     * @param res
     * @throws IOException when something unexpected happens during rest-call
     * @throws ServerException when no airlineAPI's are returned from DB
     * @throws exception.ReservationException when there are no tickets available
     */
    @Override
    public void reserveTickets(Reservation res) throws IOException, ServerException, ReservationException, Exception {
        AirlineApi airlineApi = getAirlineApi(res.getAirline()); // get api-url from airline

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(airlineApi.getUrl() + "/api/flightreservation");

        ReservationRequestDTO dto = createRequestDTO(res);
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(gson.toJson(dto, ReservationRequestDTO.class), MediaType.APPLICATION_JSON), Response.class);
        if (response.getStatus() != 200) { // not ok, so pass error message to frontend
            String re = response.readEntity(String.class);
            JsonObject jo = gson.fromJson(re, JsonObject.class);
            throw new ReservationException(jo.get("message").toString());
        } else {
            saveReservation(res);
        }
    }

    /**
     * Get AirlineApi based on a given groupName
     *
     * @return AirlineApi with a given groupName
     * @throws ServerException if returned list is empty
     */
    private AirlineApi getAirlineApi(String airlineName) throws ServerException {
        EntityManager em = emf.createEntityManager();
        AirlineApi airlineApi = new AirlineApi();
        try {
            TypedQuery<AirlineApi> query = em.createNamedQuery("AirlineApi.findbyAirlineName", AirlineApi.class).setParameter("airlineName", airlineName);
            List<AirlineApi> list = query.getResultList();
            if (list.isEmpty()) {
                throw new ServerException("Could not book the requested tickets at: " + airlineName + "! Please try again later.");
            } else {
                airlineApi = list.get(0);
            }
        } finally {
            em.close();
        }
        return airlineApi;
    }

    /**
     * Saves a Reservation to the database
     *
     * @param res
     */
    private void saveReservation(Reservation res) throws ServerException, Exception {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, res.getUser().getUserName());
        if (user == null) {
            throw new ServerException("User with username: " + res.getUser().getUserName() + " not found! Please try again later.");
        }
        res.setUser(user);

        for (Passenger p : res.getPassengers()) {
            p.setReservation(res);
        }

        em.getTransaction().begin();
        em.persist(res);
        em.getTransaction().commit();
    }

    @Override
    public void deleteReservation(long reservationID) throws NotFoundException, Exception {
        EntityManager em = emf.createEntityManager();
        try {
            Reservation reservation = em.find(Reservation.class, reservationID);
            if (reservation == null) {
                throw new NotFoundException("Reservation not found in DB");
            }
            em.getTransaction().begin();
            em.remove(reservation);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private ReservationRequestDTO createRequestDTO(Reservation res) throws Exception {
        ReservationRequestDTO dto = new ReservationRequestDTO(res.getFlightID(), res.getNumberOfSeats(), res.getReserveeName(), res.getReserveePhone(), res.getReserveeEmail());
        for (Passenger p : res.getPassengers()) {
            PassengerDTO pDto = new PassengerDTO(p.getFirstName(), p.getLastName());
            dto.addPassengers(pDto);
        }
        return dto;
    }

    private EntityManager getEntityManager() throws Exception {
        return emf.createEntityManager();
    }
}

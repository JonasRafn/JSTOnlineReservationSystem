package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import deploy.DeploymentConfiguration;
import entity.AirlineApi;
import interfaces.IReservationFacade;
import entity.Reservation;
import entity.User;
import exception.NoResultException;
import exception.ServerException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class ReservationFacade implements IReservationFacade {

    private final EntityManagerFactory emf;
    private final Gson gson;

    public ReservationFacade() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
        this.emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    }

    @Override
    public List<Reservation> getReservations(String username) throws NoResultException {
        EntityManager em = getEntityManager();
        List<Reservation> reservations = new ArrayList();
        try {
            TypedQuery<Reservation> query;
            if (username.isEmpty()) {
                query = em.createNamedQuery("Reservation.findAll", Reservation.class);
            } else {
                query = em.createNamedQuery("Reservation.findAllbyUser", Reservation.class).setParameter("userId", username);
            }
            reservations = query.getResultList();
            if (reservations.isEmpty()) {
                throw new NoResultException();
            }
        } finally {
            em.close();
        }
        for (Reservation r : reservations) {
            System.out.println(r.getAirline());
        }
        return reservations;
    }

    /**
     * Post a reservationRequest to a airline based on the groupName
     *
     * @param reservation
     * @param groupName
     * @param user
     * @return Reservation
     * @throws IOException
     * @throws ServerException
     */
    @Override
    public Reservation reservateTickets(String reservation, String groupName, String user) throws IOException, ServerException {
        AirlineApi airlineApi = getAirlineApi(groupName);

        String url = airlineApi.getUrl() + "api/flightreservation";

        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestProperty("Content-Type", "application/json;");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Method", "POST");
        con.setDoOutput(true);

        PrintWriter pw = new PrintWriter(con.getOutputStream());
        try (OutputStream os = con.getOutputStream()) {
            os.write(reservation.getBytes("UTF-8"));
        }

        int HttpResult = con.getResponseCode();
        InputStreamReader is = HttpResult < 400 ? new InputStreamReader(con.getInputStream(), "utf-8")
                : new InputStreamReader(con.getErrorStream(), "utf-8");

        Scanner responseReader = new Scanner(is);
        String response = "";
        while (responseReader.hasNext()) {
            response += responseReader.nextLine() + System.getProperty("line.separator");
        }

        Reservation res = gson.fromJson(response, Reservation.class);
        res.setAirline(airlineApi.getGroupName());
        User user1 = new User(user, "");
        res.setUser(user1);

        saveReservation(res);

        return res;
    }

    /**
     * Get AirlineApi based on a given groupName
     *
     * @return AirlineApi with a given groupName
     * @throws ServerException if returned list is empty
     */
    private AirlineApi getAirlineApi(String groupName) throws ServerException {
        EntityManager em = emf.createEntityManager();
        AirlineApi airlineApi = new AirlineApi();
        try {
            TypedQuery<AirlineApi> query = em.createNamedQuery("AirlineApi.findAirline", AirlineApi.class).setParameter("groupName", groupName);
            airlineApi = query.getSingleResult();
            if (airlineApi == null) {
                throw new ServerException("Something went wrong. Please try again");
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
    private void saveReservation(Reservation res) {
        EntityManager em = getEntityManager();
        User user = em.find(User.class, res.getUser().getUserName());
        res.setUser(user);
        em.getTransaction().begin();
        em.persist(res);
        em.getTransaction().commit();
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}

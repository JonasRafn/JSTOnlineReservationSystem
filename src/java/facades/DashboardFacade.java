package facades;

import dto.HistoryDTO;
import entity.Airport;
import entity.Destination;
import entity.PopMonth;
import interfaces.IDashboardFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import utility.Airports;

public class DashboardFacade implements IDashboardFacade {

    private EntityManagerFactory emf;

    public DashboardFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public HistoryDTO getSearchHistory() throws Exception {
        HistoryDTO history = new HistoryDTO();
        history.setNumberOfSearches(getTotalNumberOfSearches());
        history.setNumberOfAirlines(getTotalNumberOfAirlines());
        history.setNumberOfReservations(getTotalNumberOfReservations());
        history.setMostPopularDestinations(getMostPopularDestinations());
        history.setMostPopularMonths(getMostPopularMonth());
        history.setAverageNumberOfTickets(getAverageNumberOfTickets());
        return history;
    }

    private long getTotalNumberOfSearches() {
        EntityManager em = getEntityManager();
        long numberOfSearches = 0;
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(s) FROM SearchRequest s", Long.class);
            numberOfSearches = query.getSingleResult();
        } finally {
            em.close();
        }
        return numberOfSearches;
    }

    private long getTotalNumberOfAirlines() {
        EntityManager em = getEntityManager();
        long numberOfAirlines = 0;
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(a) FROM AirlineApi a", Long.class);
            numberOfAirlines = query.getSingleResult();
        } finally {
            em.close();
        }
        return numberOfAirlines;
    }

    private long getTotalNumberOfReservations() {
        EntityManager em = getEntityManager();
        long numberOfReservations = 0;
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(r) FROM Reservation r", Long.class);
            numberOfReservations = query.getSingleResult();
        } finally {
            em.close();
        }
        return numberOfReservations;
    }

    private double getAverageNumberOfTickets() {
        EntityManager em = getEntityManager();
        double averageNumberOfTickets = 0;
        try {
            TypedQuery<Object[]> query = em.createQuery("SELECT COUNT(s.id), SUM(s.numberOfTickets) FROM SearchRequest s", Object[].class);
            Object[] result = query.getSingleResult();
            double totalNumberOfSearches = Double.parseDouble(result[0].toString());
            double totalNumberOfTickets = Double.parseDouble(result[1].toString());
            averageNumberOfTickets = totalNumberOfTickets / totalNumberOfSearches;
        } finally {
            em.close();
        }
        return averageNumberOfTickets;
    }

    private List<Destination> getMostPopularDestinations() {
        EntityManager em = getEntityManager();
        List<Destination> mostPopularDestinations = new ArrayList();
        try {
            TypedQuery<Object[]> query = em.createQuery("SELECT s.destination, COUNT(s.destination) FROM SearchRequest s GROUP BY s.destination ORDER BY COUNT(s.destination) DESC", Object[].class);
            query.setMaxResults(5);
            List<Object[]> results = query.getResultList();
            Map<String, Airport> airportMap = Airports.getAirports();
            for (Object[] result : results) {
                Destination des = new Destination(result[0].toString(), (long) result[1]);
                des.setDestination(airportMap.get(des.getIataCode()).getCity());
                mostPopularDestinations.add(des);
            }

        } finally {
            em.close();
        }
        return mostPopularDestinations;
    }

    private List<PopMonth> getMostPopularMonth() {
        EntityManager em = getEntityManager();
        List<PopMonth> mostPopularMonths = new ArrayList();
        try {
            Query query = em.createNativeQuery("SELECT s.date, COUNT(s.date) FROM SearchRequest s GROUP BY YEAR(s.date), MONTH(s.date) ORDER BY COUNT(s.date) DESC");
            query.setMaxResults(5);
            List<Object[]> results = query.getResultList();
            for (Object[] result : results) {
                PopMonth month;
                String[] parts = result[0].toString().split("-");
                switch (parts[1]) {
                    case "01":
                        month = new PopMonth("January", (long) result[1]);
                        mostPopularMonths.add(month);
                        break;
                    case "02":
                        month = new PopMonth("February", (long) result[1]);
                        mostPopularMonths.add(month);
                        break;
                    case "03":
                        month = new PopMonth("Marts", (long) result[1]);
                        mostPopularMonths.add(month);
                        break;
                    case "04":
                        month = new PopMonth("April", (long) result[1]);
                        mostPopularMonths.add(month);
                        break;
                    case "05":
                        month = new PopMonth("May", (long) result[1]);
                        mostPopularMonths.add(month);
                        break;
                    case "06":
                        month = new PopMonth("June", (long) result[1]);
                        mostPopularMonths.add(month);
                        break;
                    case "07":
                        month = new PopMonth("July", (long) result[1]);
                        mostPopularMonths.add(month);
                        break;
                    case "08":
                        month = new PopMonth("August", (long) result[1]);
                        mostPopularMonths.add(month);
                        break;
                    case "09":
                        month = new PopMonth("September", (long) result[1]);
                        mostPopularMonths.add(month);
                        break;
                    case "10":
                        month = new PopMonth("October", (long) result[1]);
                        mostPopularMonths.add(month);
                        break;
                    case "11":
                        month = new PopMonth("November", (long) result[1]);
                        mostPopularMonths.add(month);
                        break;
                    case "12":
                        month = new PopMonth("December", (long) result[1]);
                        mostPopularMonths.add(month);
                        break;
                    default:
                        break;
                }
            }

        } finally {
            em.close();
        }
        return mostPopularMonths;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}

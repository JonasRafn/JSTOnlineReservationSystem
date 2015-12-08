/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.HistoryDTO;
import entity.Airport;
import entity.Destination;
import interfaces.IHistoryFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import utility.Airports;

/**
 *
 * @author sebastiannielsen
 */
public class HistoryFacade implements IHistoryFacade {

    private EntityManagerFactory emf;

    public HistoryFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public HistoryDTO getSearchHistory() {
        HistoryDTO history = new HistoryDTO();
        history.setNumberOfSearches(getTotalNumberOfSearches());
        history.setNumberOfAirlines(getTotalNumberOfAirlines());
        history.setNumberOfReservations(getTotalNumberOfReservations());
        history.setMostPopularDestinations(getMostPopularDestinations());
        history.setAverageNumberOfTickets(getAverageNumberOfTickets());
        return history;
    }
    
    private long getTotalNumberOfSearches(){
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
    
     private long getTotalNumberOfAirlines(){
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
     
   private long getTotalNumberOfReservations(){
        EntityManager em = getEntityManager();
        long numberOfReservations = 0;
//        try {
//            TypedQuery<Long> query = em.createQuery("SELECT COUNT(r) FROM reservation r", Long.class);
//            numberOfReservations = query.getSingleResult();
//        } finally {
//            em.close();
//        }
        return numberOfReservations;
    }  
     
    private double getAverageNumberOfTickets(){
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
    
    private List<Destination> getMostPopularDestinations(){
        EntityManager em = getEntityManager();
        List<Destination> mostPopularDestinations = new ArrayList();
        try {
            TypedQuery<Object[]> query = em.createQuery("SELECT s.destination, COUNT(s.destination) FROM SearchRequest s GROUP BY s.destination ORDER BY COUNT(s.destination) DESC", Object[].class);
            query.setMaxResults(5);
            List<Object[]> results = query.getResultList();
            Map<String, Airport> airportMap = Airports.getAirports();
            for (Object[] result : results) {
                Destination des = new Destination(result[0].toString(), (long)result[1]);
                des.setDestination(airportMap.get(des.getIataCode()).getCity());
                mostPopularDestinations.add(des);
            }
            
        } finally {
            em.close();
        }
        return mostPopularDestinations;
    }
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
}

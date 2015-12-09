/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entity.Destination;
import entity.PopMonth;
import java.util.List;

/**
 *
 * @author sebastiannielsen
 */
public class HistoryDTO {
    private long numberOfSearches;
    private List<Destination> mostPopularDestinations;
    private List<PopMonth> mostPopularMonths;
    private double averageNumberOfTickets;
    private long numberOfAirlines;
    private long numberOfReservations;

    public HistoryDTO() {
    }

    public long getNumberOfSearches() {
        return numberOfSearches;
    }
    
    public void setNumberOfReservations(long numberOfReservations){
        this.numberOfReservations = numberOfReservations;
    }
    
    public long getNumberOfReservations(){
        return numberOfReservations;
    }

    public void setNumberOfSearches(long numberOfSearches) {
        this.numberOfSearches = numberOfSearches;
    }

    public List getMostPopularDestinations() {
        return mostPopularDestinations;
    }

    public void setMostPopularDestinations(List<Destination> mostPopularDestinations) {
        this.mostPopularDestinations = mostPopularDestinations;
    }

    public double getAverageNumberOfTickets() {
        return averageNumberOfTickets;
    }

    public void setAverageNumberOfTickets(double averageNumberOfTickets) {
        this.averageNumberOfTickets = averageNumberOfTickets;
    }

    public long getNumberOfAirlines() {
        return numberOfAirlines;
    }

    public void setNumberOfAirlines(long numberOfAirlines) {
        this.numberOfAirlines = numberOfAirlines;
    }

    public List<PopMonth> getMostPopularMonths() {
        return mostPopularMonths;
    }

    public void setMostPopularMonths(List<PopMonth> mostPopularMonths) {
        this.mostPopularMonths = mostPopularMonths;
    }
    
    
}

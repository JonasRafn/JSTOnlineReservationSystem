package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SearchRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String origin;
    private String destination;
    private String date;
    private int numberOfTickets;

    public SearchRequest() {
    }

    public SearchRequest(String origin, String date, int numberOfTickets) {
        this.origin = origin;
        this.date = date;
        this.numberOfTickets = numberOfTickets;
    }

    public SearchRequest(String origin, String destination, String date, int numberOfTickets) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.numberOfTickets = numberOfTickets;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
}

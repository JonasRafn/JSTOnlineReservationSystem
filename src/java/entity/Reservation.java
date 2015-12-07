package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Reservation implements Serializable {
    
    @Column(name="reservation_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="airline")
    private String airline;
    
    @Column(name="flightID")
    private String flightID;
    
    @Column(name="number_of_seats")
    private int numberOfSeats;
    
    @Column(name="date")
    private Date date;
    
    @Column(name="total_price")
    private float totalPrice;
    
    @Column(name="price_person")
    private float pricePerson;
    
    @Column(name="traveltime")
    private int traveltime;
    
    @Column(name="origin")
    private String origin;
    
    @Column(name="origin_city")
    private String originCity;
    
    @Column(name="destination")
    private String destination;
    
    @Column(name="destinationCity")
    private String destinationCity;
    
    @Column(name="destination_date")
    private Date destinationDate;
    
    @JoinColumn(name="passengers")
    private List<Passenger> passengers;
    
    @Column(name="user")
    private User user;
    
    
    public Reservation() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

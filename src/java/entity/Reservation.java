package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

@Entity
public class Reservation implements Serializable {

    @Column(name = "reservation_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "airline")
    private String airline;

    @Column(name = "flightID")
    private String flightID;

    @Column(name = "number_of_seats")
    private int numberOfSeats;

    @Column(name = "date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date Date;

    @Column(name = "total_price")
    private float totalPrice;

    @Column(name = "price_person")
    private float pricePerson;

    @Column(name = "traveltime")
    private int FlightTime;

    @Column(name = "origin")
    private String Origin;

    @Column(name = "origin_city")
    private String originCity;

    @Column(name = "destination")
    private String Destination;

    @Column(name = "destinationCity")
    private String destinationCity;

    @Column(name = "destination_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date destinationDate;

    @Column(name = "reserveeName")
    private String ReserveeName;

    @JoinColumn(name = "passenger_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Passenger> Passengers;

    @JoinColumn(name = "user_id")
    @OneToOne
    private User user;

    public Reservation() {

    }

    public Long getId() {
        return id;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        this.Date = date;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getPricePerson() {
        return pricePerson;
    }

    public void setPricePerson(float pricePerson) {
        this.pricePerson = pricePerson;
    }

    public int getTraveltime() {
        return FlightTime;
    }

    public void setTraveltime(int traveltime) {
        this.FlightTime = traveltime;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        this.Origin = origin;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        this.Destination = destination;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public Date getDestinationDate() {
        return destinationDate;
    }

    public void setDestinationDate(Date destinationDate) {
        this.destinationDate = destinationDate;
    }

    public List<Passenger> getPassengers() {
        return Passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.Passengers = passengers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReserveeName() {
        return ReserveeName;
    }

    public void setReserveeName(String reserveeName) {
        this.ReserveeName = reserveeName;
    }

}

package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

@NamedQueries({
    @NamedQuery(name = "Reservation.findAllbyUser", query = "SELECT r FROM Reservation r WHERE r.user = :user"),
    @NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r")})
@Entity
public class Reservation implements Serializable {

    @Column(name = "reservation_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "airline")
    private String airline;

    @Column(name = "flight_ID")
    private String flightID;

    @Column(name = "number_of_seats")
    private int numberOfSeats;

    @Column(name = "date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "total_price")
    private float totalPrice;

    @Column(name = "price_person")
    private float pricePerson;

    @Column(name = "traveltime")
    private int flightTime;

    @Column(name = "origin")
    private String origin;

    @Column(name = "origin_city")
    private String originCity;

    @Column(name = "destination")
    private String destination;

    @Column(name = "destination_city")
    private String destinationCity;

    @Column(name = "destination_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date destinationDate;

    @Column(name = "reservee_name")
    private String reserveeName;

    @Column(name = "reservee_phone")
    private String reserveePhone;

    @Column(name = "reservee_email")
    private String reserveeEmail;

    @JoinColumn(name = "passenger_id")
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<Passenger> passengers = new ArrayList();

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
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        return flightTime;
    }

    public void setTraveltime(int traveltime) {
        this.flightTime = traveltime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }
    
    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReserveeName() {
        return reserveeName;
    }

    public void setReserveeName(String reserveeName) {
        this.reserveeName = reserveeName;
    }

    public int getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(int flightTime) {
        this.flightTime = flightTime;
    }

    public String getReserveePhone() {
        return reserveePhone;
    }

    public void setReserveePhone(String reserveePhone) {
        this.reserveePhone = reserveePhone;
    }

    public String getReserveeEmail() {
        return reserveeEmail;
    }

    public void setReserveeEmail(String reserveeEmail) {
        this.reserveeEmail = reserveeEmail;
    }
}

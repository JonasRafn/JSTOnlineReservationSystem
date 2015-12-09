package dto;

import entity.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReservationDTO implements Serializable {

    private String airline;

    private String flightID;

    private int numberOfSeats;

    private Date date;

    private float totalPrice;

    private float pricePerson;

    private int flightTime;

    private String origin;

    private String originCity;

    private String destination;

    private String destinationCity;

    private Date destinationDate;

    private String reserveeName;

    private List<Passenger> passengers;

    private User user;

    public ReservationDTO() {

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

}

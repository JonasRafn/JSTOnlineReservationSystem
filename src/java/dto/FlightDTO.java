package dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FlightDTO {

    private String flightID;
    private int numberOfSeats;
    private Date date;
    private float totalPrice;
    private float pricePerson;
    private int traveltime;
    private String origin;
    private String originCity;
    private String destination;
    private String destinationCity;
    private Date destinationDate;

    public FlightDTO() {
    }
    
    public FlightDTO(String flightID, int numberOfSeats, Date date, float totalPrice, int traveltime, String origin, String destination) {
        this.flightID = flightID;
        this.numberOfSeats = numberOfSeats;
        this.date = date;
        this.totalPrice = totalPrice;
        this.traveltime = traveltime;
        this.origin = origin;
        this.destination = destination;
    }

    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public void formatFlightIDCode(String str) {
        List<String> flightIDList = Arrays.asList(flightID.split("x"));
        this.flightID = flightIDList.get(0);
    }
    
    public void calculatePricePerPerson() {
        this.pricePerson = totalPrice / numberOfSeats;
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

    public int getTraveltime() {
        return traveltime;
    }

    public void setTraveltime(int traveltime) {
        this.traveltime = traveltime;
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

    public Date getDestinationDate() {
        return destinationDate;
    }

    public void setDestinationDate(Date destinationDate) {
        this.destinationDate = destinationDate;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }
}

package dto;

import exception.ServerException;
import java.util.Date;
import static utility.TimeConverter.toDate;
import static utility.TimeConverter.toStringDate;

public class FlightDTO {

    private String flightID;
    private int numberOfSeats;
    private String date;
    private float totalPrice;
    private float pricePerson;
    private int traveltime;
    private String origin;
    private String originCity;
    private String destination;
    private String destinationCity;
    private String destinationDate;

    public FlightDTO() {
    }

    public Date getDate() throws ServerException {
        return toDate(date);
    }

    public Date getDestinationDate() throws ServerException {
        return toDate(destinationDate);
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public String getStringDate() {
        return date;
    }

    public void setDestinationDate(Date destinationDate) {
        System.out.println("########DATE IS: " + destinationDate);
        this.destinationDate = toStringDate(destinationDate);
    }

    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public void calculatePricePerPerson() {
        this.pricePerson = totalPrice / (float) numberOfSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
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

    public String getStringDestinationDate() {
        return destinationDate;
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

    public float getPricePerson() {
        return pricePerson;
    }

    public void setPricePerson(float pricePerson) {
        this.pricePerson = pricePerson;
    }

}

package dto;

import java.util.Date;

public class FlightDTO {

    private String flightId;
    private int numberOfSeats;
    private Date date;
    private float priceTotal;
    private int travelTime;
    private String origin;
    private String destination;

    public FlightDTO(String flightId, int numberOfSeats, Date date, float priceTotal, int travelTime, String origin, String destination) {
        this.flightId = flightId;
        this.numberOfSeats = numberOfSeats;
        this.date = date;
        this.priceTotal = priceTotal;
        this.travelTime = travelTime;
        this.origin = origin;
        this.destination = destination;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
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

    public float getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(float priceTotal) {
        this.priceTotal = priceTotal;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
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
}

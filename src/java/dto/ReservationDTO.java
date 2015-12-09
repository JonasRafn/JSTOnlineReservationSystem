package dto;

import entity.Passenger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import utility.TimeConverter;

public class ReservationDTO {

    private String airline;
    private String flightID;
    private int numberOfSeats;
    private String date;
    private float totalPrice;
    private float pricePerson;
    private int flightTime;
    private String origin;
    private String originCity;
    private String destination;
    private String destinationCity;
    private String destinationDate;
    private String reserveeName;
    private String reserveePhone;
    private String reserveeEmail;
    private List<PassengerDTO> passengers;

    public ReservationDTO(String airline, String flightID, int numberOfSeats, Date date, float totalPrice, float pricePerson, int flightTime, String origin, String originCity, String destination, String destinationCity, Date destinationDate, String reserveeName, String reserveePhone, String reserveeEmail, List<Passenger> passengers) {

        this.airline = airline;
        this.flightID = flightID;
        this.numberOfSeats = numberOfSeats;
        this.date = TimeConverter.convertDate(date);
        this.totalPrice = totalPrice;
        this.pricePerson = pricePerson;
        this.flightTime = flightTime;
        this.origin = origin;
        this.originCity = originCity;
        this.destination = destination;
        this.destinationCity = destinationCity;
        this.destinationDate = TimeConverter.convertDate(destinationDate);
        this.reserveeName = reserveeName;
        this.reserveePhone = reserveePhone;
        this.reserveeEmail = reserveeEmail;
        this.passengers = convertPassengerDTO(passengers);
    }

    private List<PassengerDTO> convertPassengerDTO(List<Passenger> passengerList) {
        List<PassengerDTO> dtoList = new ArrayList();
        for (Passenger p : passengerList) {
            PassengerDTO dto = new PassengerDTO(p.getFirstName(), p.getLastName());
            dtoList.add(dto);
        }
        return dtoList;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public int getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(int flightTime) {
        this.flightTime = flightTime;
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

    public String getDestinationDate() {
        return destinationDate;
    }

    public void setDestinationDate(String destinationDate) {
        this.destinationDate = destinationDate;
    }

    public String getReserveeName() {
        return reserveeName;
    }

    public void setReserveeName(String reserveeName) {
        this.reserveeName = reserveeName;
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

    public List<PassengerDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerDTO> passengers) {
        this.passengers = passengers;
    }
}

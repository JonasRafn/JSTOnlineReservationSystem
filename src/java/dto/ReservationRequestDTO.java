package dto;

import java.util.ArrayList;
import java.util.List;

public class ReservationRequestDTO {

    private String flightID;
    private int numberOfSeats;
    private String ReserveeName;
    private String ReservePhone;
    private String ReserveeEmail;
    private List<PassengerDTO> Passengers;

    public ReservationRequestDTO(String flightID, int numberOfSeats, String ReserveeName, String ReservePhone, String ReserveeEmail) {
        Passengers = new ArrayList();
        this.flightID = flightID;
        this.numberOfSeats = numberOfSeats;
        this.ReserveeName = ReserveeName;
        this.ReservePhone = ReservePhone;
        this.ReserveeEmail = ReserveeEmail;
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

    public String getReserveeName() {
        return ReserveeName;
    }

    public void setReserveeName(String ReserveeName) {
        this.ReserveeName = ReserveeName;
    }

    public String getReservePhone() {
        return ReservePhone;
    }

    public void setReservePhone(String ReservePhone) {
        this.ReservePhone = ReservePhone;
    }

    public String getReserveeEmail() {
        return ReserveeEmail;
    }

    public void setReserveeEmail(String ReserveeEmail) {
        this.ReserveeEmail = ReserveeEmail;
    }

    public List<PassengerDTO> getPassengers() {
        return Passengers;
    }

    public void addPassengers(PassengerDTO passenger) {
        Passengers.add(passenger);
    }
}
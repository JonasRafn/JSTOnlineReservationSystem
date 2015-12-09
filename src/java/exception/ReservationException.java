package exception;

public class ReservationException extends Exception {

    public ReservationException(String message) {
        super(message);
    }

    public ReservationException() {
        super("Something went wrong, could not reserve the requested tickets, try again!");
    }
}

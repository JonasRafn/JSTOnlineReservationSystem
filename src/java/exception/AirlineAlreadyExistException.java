package exception;

public class AirlineAlreadyExistException extends Exception {

    /**
     * A user with that name already exist
     *
     * @param message
     */
    public AirlineAlreadyExistException(String message) {
        super(message);
    }

    public AirlineAlreadyExistException() {
        super("Airline with that url already exist!");
    }
}

package exception;

public class UserAlreadyExistException extends Exception {

    /**
     * A user with that name already exist
     *
     * @param message
     */
    public UserAlreadyExistException(String message) {
        super(message);
    }

    public UserAlreadyExistException() {
        super("User with that name already exist!");
    }
}

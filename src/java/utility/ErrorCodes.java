package utility;

public class ErrorCodes {

    public static String getServerErrorMsg(String message) {
        return "Something went wrong. Please try again. Cause: " + message;
    }
    
    public static String getIllegalInputErrorMsg(String message) {
        return "Illegal input: " + message;
    }
    
    public static String getInvalidDateErrorMsg(String stringDate) {
        return "Erroneous Date: '" + stringDate + "' is not a valid date";
    } 
    
    public static String getMalformedJsonMsg(String message) {
        return "Malformed json: " + message;
    }
    
    public static String getUserAlreadyExistsMsg(String message) {
        return "User with user-name: " + message + " already exists";
    }
}

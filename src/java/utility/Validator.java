package utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import dto.AirlineDTO;
import dto.FlightDTO;
import dto.PassengerDTO;
import dto.ReservationRequestDTO;
import exception.ServerException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static utility.ErrorCodes.getServerErrorMsg;
import static utility.ErrorCodes.getIllegalInputErrorMsg;
import static utility.ErrorCodes.getInvalidDateErrorMsg;
import static utility.ErrorCodes.getMalformedJsonMsg;

public class Validator {

    private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();

    /**
     * Checks that stringDate complies with a ISO8601-date
     *
     * @param stringDate
     * @return the stringDate to ISO8601-date
     * @throws exception.ServerException
     */
    public static String validateDate(String stringDate) throws ServerException {
        DateFormat ISO8601Date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date date = ISO8601Date.parse(stringDate);
        } catch (ParseException ex) {
            throw new ServerException(getInvalidDateErrorMsg(stringDate));
        }
        return stringDate;
    }

    /**
     * Returns string tickets as an integer
     *
     * @param numTickets
     * @return tickets as integer
     * @throws exception.ServerException
     */
    public static int validateTickets(String numTickets) throws ServerException {
        try {
            int parsedPersons = Integer.parseInt(numTickets);
            if (parsedPersons <= 0) {
                throw new ServerException(getServerErrorMsg("Number of tickets can't be less than: " + numTickets));
            }
            return parsedPersons;
        } catch (NumberFormatException e) {
            throw new ServerException(getIllegalInputErrorMsg("Number of tickets is not a number: " + numTickets));
        }
    }

    public static AirlineDTO validateJson(String jsonString) throws ServerException {
        AirlineDTO airlineDTO = new AirlineDTO();

        //try to parse json-string using AirlineDTO as template or throw exception
        try {
            airlineDTO = GSON.fromJson(jsonString, AirlineDTO.class);
        } catch (JsonSyntaxException ex) {
            throw new ServerException(getMalformedJsonMsg("JsonSyntaxException when gson.fromJson"));
        }

        //check if airline-name is null and throw exception if this is the case
        try {
            airlineDTO.getAirline();
        } catch (NullPointerException ex) {
            throw new ServerException(getMalformedJsonMsg("Invalid airline name"));
        }

        if (airlineDTO.getAirline() == null) {
            throw new ServerException(getMalformedJsonMsg("Invalid airline name"));
        }

        //check if any attributes on a flight is invalid and remove from list if invalid
        for (int i = 0; i < airlineDTO.getFlights().size(); i++) {
            FlightDTO dto = airlineDTO.getFlights().get(i);
            if (dto.getStringDate() == null || dto.getFlightID() == null || dto.getDestination() == null || dto.getOrigin() == null
                    || dto.getStringDate() == null || dto.getNumberOfSeats() == 0 || dto.getTotalPrice() == 0f || dto.getTraveltime() == 0) {
                throw new ServerException(getMalformedJsonMsg("invalid flight-object in jsonresponse"));
            }
        }
        return airlineDTO; //return valid airline with at least one valid flight
    }
}

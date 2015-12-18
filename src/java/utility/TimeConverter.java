package utility;

import exception.ServerException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeConverter {

    public static String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final TimeZone utc = TimeZone.getTimeZone("Europe/Copenhagen");
    private static final SimpleDateFormat isoFormatter = new SimpleDateFormat(ISO_FORMAT);

    static {
        isoFormatter.setTimeZone(utc);
    }

    public static String toStringDate(Date date) {
        String iso8601String = isoFormatter.format(date);
        return iso8601String;
    }

    public static Date toDate(String iso8601String) throws ServerException {
        Date date = new Date();
        try {
            DateFormat sdfISO = new SimpleDateFormat(ISO_FORMAT);
            date = sdfISO.parse(iso8601String);
        } catch (ParseException ex) {
            throw new ServerException("Unparseble iso8601 string");
        }
        return date;
    }
}

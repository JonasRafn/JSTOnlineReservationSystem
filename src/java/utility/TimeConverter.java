package utility;

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

    public static String convertDate(Date date) {
        String iso8601String = isoFormatter.format(date);
        return iso8601String;
    }

}

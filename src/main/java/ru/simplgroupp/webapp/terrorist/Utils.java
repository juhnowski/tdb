package ru.simplgroupp.webapp.terrorist;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 12.08.2015
 * 14:26
 */

public class Utils {
    public static Boolean isValidDate(String inputValue) {
        Calendar cal = new GregorianCalendar();
        cal.setLenient(false);
        cal.clear ();
        try {
            int d = Integer.parseInt (inputValue.substring (0, 2));
            int m = Integer.parseInt (inputValue.substring (3, 5));
            int y = Integer.parseInt (inputValue.substring (6, 10));
            cal.set(y, m - 1, d);
            java.util.Date dt = cal.getTime();
            return true;
        }
        catch (NumberFormatException nfe) {return false;}
        catch (IllegalArgumentException iae) {return false;}
        catch (StringIndexOutOfBoundsException e) {return false;}
        catch (Exception e) {return false;}
    }
}

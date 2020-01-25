package train.apitrainclient.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Converts number of seconds in time format in string 00:00:00
 * Converts string to date objects and vice versa as well.
 * Converts system date to UTC and versa as well.
 */

public class TimeUtils {

    public static String getTimeString(int seconds) {
        int hours = seconds / 3600;
        seconds = seconds % 3600;

        int minutes = seconds / 60;
        seconds = seconds % 60;

        String hoursStr = hours + "";
        String minuteStr = minutes + "";
        String secondsStr = seconds + "";

        if (hours < 10) {
            hoursStr = "0" + hoursStr;
        }

        if (minutes < 10) {
            minuteStr = "0" + minuteStr;
        }

        if (seconds < 10) {
            secondsStr = "0" + secondsStr;
        }
        if (hoursStr.equalsIgnoreCase("00")) {
            return minuteStr + ":" + secondsStr;

        } else {
            return hoursStr + ":" + minuteStr + ":" + secondsStr;

        }
    }

    public static String getTimeString(int seconds, int numberOfTimeItems) {
        int hours = seconds / 3600;
        seconds = seconds % 3600;

        int minutes = seconds / 60;
        seconds = seconds % 60;

        String hoursStr = hours + "";
        String minuteStr = minutes + "";
        String secondsStr = seconds + "";

        if (hours < 10) {
            hoursStr = "0" + hoursStr;
        }

        if (minutes < 10) {
            minuteStr = "0" + minuteStr;
        }

        if (seconds < 10) {
            secondsStr = "0" + secondsStr;
        }

        String timeStr = "";
        if (numberOfTimeItems == 1) {
            timeStr = secondsStr + "";
        } else if (numberOfTimeItems == 2) {
            timeStr = minuteStr + ":" + secondsStr;
        } else if (numberOfTimeItems == 3) {
            timeStr = hoursStr + ":" + minuteStr + ":" + secondsStr;
        }

        return timeStr;
    }

    public static String getEstimatedDurationTimeString(int seconds) {
        int hours = seconds / 3600;
        seconds = seconds % 3600;

        int minutes = seconds / 60;
        seconds = seconds % 60;

        String hoursStr = hours + "";
        String minuteStr = minutes + "";

        if (hours < 10) {
            hoursStr = "0" + hoursStr;
        }

        if (minutes < 10) {
            minuteStr = "0" + minuteStr;
        }

        if (hoursStr.equalsIgnoreCase("00")) {
            return minuteStr + " min.";

        } else {
            return hoursStr + " hour " + minuteStr + " min.";

        }
    }

    public static Date GetStandardDate(Date original) {
        Date converted = original;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss Z");

        SimpleDateFormat sdf1 = new SimpleDateFormat("Z");

        String timeZone = sdf1.format(original);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String datestr = sdf.format(original).replace("+0000", timeZone);
        try {
            converted = sdf.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return converted;
    }


    public static Date ConvertToUTCDate(String stringDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.parse(stringDate);
    }

    public static String ConvertToUTCDateString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public static String toString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static Date toDate(String dateString) {
        if (dateString == null) {
            return null;
        }

        Date converted = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        try {
            converted = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return converted;
    }

    public static String getCurrentDate() {
        String currentDate = "";
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = simpleDateFormat.format(calendar.getTime());
        return currentDate;

    }

    static public int calculateDayCount(String beginDate, String endDate) {
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
        int days = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date datebegin = sdf.parse(beginDate);
            Date dateEnd = sdf.parse(endDate);
            cal1.setTime(datebegin);
            cal2.setTime(dateEnd);
            Date d1 = cal1.getTime();
            Date d2 = cal2.getTime();
            if (d2.getTime() >= d1.getTime()) {
                days = (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
            } else {
                days = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return days;
    }

    public static int getDiffYears(String DOB, String currentDate) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        int diff=0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date a = sdf.parse(DOB);
            Date b = sdf.parse(currentDate);
             diff = b.getYear()- a.getYear();
            if (a.getMonth() > b.getMonth() ||
                    (a.getMonth() == b.getMonth() && a.getDate()> b.getDate())) {
                diff--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
    }

    public static String dateAfterDay(String startDate, int dayCount) {
        String newDate = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(startDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, dayCount);
            newDate = sdf.format(c.getTime());
            System.out.println("Date Incremented by One: " + newDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;

    }

    public static String dateAfterMonth(String startDate, int monthCount) {
        String finalDate = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.MONTH, monthCount);
        String newDate = sdf.format(c.getTime());
        System.out.println("Date Incremented by One: " + newDate);

        return newDate;
    }

    public static String dateAfterYear(String startDate, int yearCount) {
        String finalDate = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.YEAR, yearCount);
        String newDate = sdf.format(c.getTime());
        System.out.println("Date Incremented by One: " + newDate);

        return newDate;
    }
}

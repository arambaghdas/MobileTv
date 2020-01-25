package train.apitrainclient.utils;

import android.content.Context;
import android.graphics.Point;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.pttrackershared.models.eventbus.Clients;

public class GraphHelpers {
    public static boolean fromHomeScreen = true;
    public static Clients selectedUser;
    public static String getDayAndMonthInstring(String date){
        //get date with month as short String
        String dateFormat[] = date.split("/");
        String month = dateFormat[1];
        if (month.equalsIgnoreCase("1") || month.equalsIgnoreCase("01")){
            month = "Jan";
        }else if (month.equalsIgnoreCase("2") || month.equalsIgnoreCase("02")){
            month = "Feb";
        }else if (month.equalsIgnoreCase("3") || month.equalsIgnoreCase("03")){
            month = "Mar";
        }else if (month.equalsIgnoreCase("4") || month.equalsIgnoreCase("04")){
            month = "Apr";
        }else if (month.equalsIgnoreCase("5") || month.equalsIgnoreCase("05")){
            month = "May";
        }else if (month.equalsIgnoreCase("6") || month.equalsIgnoreCase("06")){
            month = "Jun";
        }else if (month.equalsIgnoreCase("7") || month.equalsIgnoreCase("07")){
            month = "Jul";
        }else if (month.equalsIgnoreCase("8") || month.equalsIgnoreCase("08")){
            month = "Aug";
        }else if (month.equalsIgnoreCase("9") || month.equalsIgnoreCase("09")){
            month = "Sep";
        }else if (month.equalsIgnoreCase("10")){
            month = "Oct";
        }else if (month.equalsIgnoreCase("11")){
            month = "Nov";
        }else if (month.equalsIgnoreCase("12")){
            month = "Dec";
        }

        return dateFormat[0] + " " + month;
    }

    public static ArrayList<String> dateInterval(String startDay, String finalDay) {
        //getting dates that are with no populated graphs
        ArrayList<Date> dates = new ArrayList<Date>();
        ArrayList<String>stringDates = new ArrayList<>();

        String str_date = startDay;
        String end_date = finalDay;
        if (str_date != null && str_date.length() > 0 && end_date != null && end_date.length() > 0){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate = null;
            try {
                startDate = formatter.parse(str_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date  endDate = null;
            try {
                endDate = formatter.parse(end_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long interval = 24*1000 * 60 * 60; // 1 hour in millis
            long endTime = endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
            long curTime = startDate.getTime();
            while (curTime <= endTime) {
                dates.add(new Date(curTime));
                curTime += interval;
            }
            for(int i=0;i<dates.size();i++){
                Date lDate =(Date)dates.get(i);
                String ds = formatter.format(lDate);
                stringDates.add(ds);
            }

            return stringDates;
        }else {
            return new ArrayList<>();
        }

    }

    public static int getDeviceDensityBmi(Context context) {
        //getting density of the device screen is importan for drawing lines...if some device have problems with drawing it can be corrected from here
        //for Bmi graph
        switch (context.getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
            case DisplayMetrics.DENSITY_MEDIUM:
            case DisplayMetrics.DENSITY_TV:
            case DisplayMetrics.DENSITY_HIGH:
            case DisplayMetrics.DENSITY_260:
            case DisplayMetrics.DENSITY_280:
            case DisplayMetrics.DENSITY_300:
            case DisplayMetrics.DENSITY_XHIGH:
                if (fromHomeScreen)
                return 65;
                else
                return 160;
            case DisplayMetrics.DENSITY_340:
            case DisplayMetrics.DENSITY_360:
            case DisplayMetrics.DENSITY_400:
            case DisplayMetrics.DENSITY_420:
            case DisplayMetrics.DENSITY_XXHIGH:
                if (fromHomeScreen)
                    return 65;
                else
                    return 160;
            case DisplayMetrics.DENSITY_560:
            case DisplayMetrics.DENSITY_XXXHIGH:
        }

        if (fromHomeScreen)
            return 65;
        else
            return 160;
    }

    public static int getDeviceDensityString(Context context) {
        //getting density of the device screen is importan for drawing lines...if some device have problems with drawing it can be corrected from here
        //for weight graph
        switch (context.getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
            case DisplayMetrics.DENSITY_MEDIUM:
            case DisplayMetrics.DENSITY_TV:
            case DisplayMetrics.DENSITY_HIGH:
            case DisplayMetrics.DENSITY_260:
            case DisplayMetrics.DENSITY_280:
            case DisplayMetrics.DENSITY_300:
            case DisplayMetrics.DENSITY_XHIGH:
                if (fromHomeScreen)
                    return 65;
                else
                    return 160;
            case DisplayMetrics.DENSITY_340:
            case DisplayMetrics.DENSITY_360:
            case DisplayMetrics.DENSITY_400:
            case DisplayMetrics.DENSITY_420:
            case DisplayMetrics.DENSITY_XXHIGH:
                if (fromHomeScreen)
                    return 65;
                else
                    return 160;
            case DisplayMetrics.DENSITY_560:
            case DisplayMetrics.DENSITY_XXXHIGH:
        }

        if (fromHomeScreen)
            return 65;
        else
            return 160;
    }

    public static int GetDipsFromPixel(Context context,float pixels) {
        //setting values for weight and positions so can function on diff devices
        // Get the screen's density scale
        final float scale = context.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }


    public static void changeTextView(TextView textView, boolean kg) {
        //changing table to show text from graph with kg next to it
        String text = textView.getText().toString();
        if (kg){

            if (text.contains("kg") || text.contains("lb")){
                text = text.substring(0,text.length() - 3);
            }
        }else {
            if (text.contains("kg")){
                text = text.substring(0,text.length() - 3);
            }

            textView.setText(text);
        }
    }

    public int convertKgToPound(int kg){
        int pound = (int) (kg * 2.20);

       return pound;
    }

    public static String getDiffFromPrevDay(float previous,float current,boolean bmi){
        //get diff from previous day bmi or weight
        float result = 0;
        int resultWieght = 0;
        String operator = "";

        if (previous > current){
            result  = previous - current;
            operator = "- ";
        }else {
            result  = current - previous;
            operator = "+ ";
        }

        if (bmi){
            try {
                float f = result;
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                float twoDigitsF = Float.valueOf(decimalFormat.format(f));
                result = twoDigitsF;
            }catch (Exception e){
                e.printStackTrace();
                result = 0;
            }

        }
        resultWieght = (int) result;
        if (!bmi){
            return operator + resultWieght;
        }else {
            return operator + result;
        }

    }

    public static Point getLocationOnScreen(View view) {
        //getting location on the screen for the view
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new Point(location[0], location[1]);
    }

    public static String convertDateForDDMM(Date date){
        //converting date to give date in num month in string
        String dateDDMM = "";
        String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
        String day          = (String) DateFormat.format("dd",   date); // 20
        String monthString  = (String) DateFormat.format("MMM",  date); // Jun
        String monthNumber  = (String) DateFormat.format("MM",   date); // 06
        String year         = (String) DateFormat.format("yyyy", date); // 2013

        return day + ":" + monthString;
    }

    public static String getTodaysDate(Date date){
        //converting date to give date in num month in string for moveToDateCheck
        String dateDDMM = "";
        String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
        String day          = (String) DateFormat.format("dd",   date); // 20
        String monthString  = (String) DateFormat.format("MMM",  date); // Jun
        String monthNumber  = (String) DateFormat.format("MM",   date); // 06
        String year         = (String) DateFormat.format("yyyy", date); // 2013

        return day + " " + monthString;
    }

    public static String getDateInString(Date date){

        String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
        String day          = (String) DateFormat.format("dd",   date); // 20
        String monthString  = (String) DateFormat.format("MM",  date); // Jun
        String monthNumber  = (String) DateFormat.format("MM",   date); // 06
        String year         = (String) DateFormat.format("yyyy", date); // 2013

        return day + "/" + changeMonthInNumber(monthString) + "/" + year;
    }

    public static String changeMonthInNumber(String month){
        //get date with month as short String
        if (month.equalsIgnoreCase("Jan")){
            month = "01";
        }else if (month.equalsIgnoreCase("Feb")){
            month = "02";
        }else if (month.equalsIgnoreCase("Mar")){
            month = "03";
        }else if (month.equalsIgnoreCase("Apr")){
            month = "04";
        }else if (month.equalsIgnoreCase("May")){
            month = "05";
        }else if (month.equalsIgnoreCase("Jun")){
            month = "06";
        }else if (month.equalsIgnoreCase("Jul")){
            month = "07";
        }else if (month.equalsIgnoreCase("Aug")){
            month = "08";
        }else if (month.equalsIgnoreCase("Sep")){
            month = "09";
        }else if (month.equalsIgnoreCase("Oct")){
            month = "10";
        }else if (month.equalsIgnoreCase("Nov")){
            month = "11";
        }else if (month.equalsIgnoreCase("Dec")){
            month = "12";
        }

        return month;
    }

}

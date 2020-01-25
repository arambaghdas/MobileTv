package com.pttracker.trainingaid.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;

/**
 * Defines the utility methods that could be used at multiple places
 */

public class AppUtils {
    public static boolean networkConnection = true;
    /**
     * Converts kilograms to pounds
     */
    public static double convertKgToPound(double kilograms) {

        return (double) Math.round((kilograms * 2.20462262) * 100.0) / 100.0;
    }

    public static double convertPoundToKg(double pounds) {

        return (double) Math.round((pounds / 2.20462262) * 100.0) / 100.0;
    }

    /**
     * Checks whether two calendar dates
     *
     * @param calendar1
     * @param calendar2 are same day dates. It is not mandatory to have same hours, minutes, seconds and milliseconds
     * @return
     */
    public static boolean AreSameDay(Calendar calendar1, Calendar calendar2) {
        return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * Checks whether two date objects
     *
     * @param date1
     * @param date2 are same day dates. It is not mandatory to have same hours, minutes, seconds and milliseconds
     * @return
     */
    public static boolean AreSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR));
    }

    public static byte[] getPictureByteOfArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return reduceImage(byteArrayOutputStream.toByteArray());
    }

    public static byte[] reduceImage(byte[] data) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        o.inPurgeable = true;
        o.inInputShareable = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, o);

        // The new size we want to scale to
        final int REQUIRED_H = 70;
        final int REQUIRED_W = 150;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_W || height_tmp / 2 < REQUIRED_H) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inPurgeable = true;
        o2.inInputShareable = true;
        o2.inSampleSize = scale;
        Bitmap bitmapScaled = null;
        bitmapScaled = BitmapFactory.decodeByteArray(data, 0, data.length, o2);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapScaled.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}

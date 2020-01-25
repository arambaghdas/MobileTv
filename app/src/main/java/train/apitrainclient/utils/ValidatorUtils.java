package train.apitrainclient.utils;

import java.util.List;

/**
 * ValidatorUtils is a reusable class to check validity of multiple items
 */

public class ValidatorUtils {

    /**
     * Validates whether a provided string
     * @param value is null or empty
     * @return
     */
    public static boolean IsNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean IsNullOrEmpty(List list) {
        return list == null || list.isEmpty();
    }


    /**
     * Validates whether a provided string
     * @param value is a valid email address or not
     * @return
     */
    public static boolean IsValidEmail(String value) {
        if (value == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches();
        }
    }
}

package train.apitrainclient.utils;

import android.util.Log;


/**
 * LoggerUtils shows app log with descriptive info and decides whether to display logs or not
 */

public class LoggerUtils {

    private static final boolean IS_DEBUG = true;
    public static void e(String message) {
        if(IS_DEBUG) {
            message = getPrettyMessage(message);
            Log.e("LoggerUtils", message);
        }
    }

    public static void d(String message) {
        if(IS_DEBUG) {
            message = getPrettyMessage(message);
            Log.d("LoggerUtils", message);
        }
    }

    private static String getPrettyMessage(String message) {
        int requiredDepth = 4;
        StackTraceElement[] ste = Thread.currentThread().getStackTrace();

        if (ste == null || requiredDepth >= ste.length) {
            return message;
        }

        StackTraceElement requiredSte = ste[requiredDepth];
        return requiredSte.getLineNumber() + ": " + requiredSte.getClassName() + "::" + requiredSte.getMethodName() + ": " + message;
    }
}

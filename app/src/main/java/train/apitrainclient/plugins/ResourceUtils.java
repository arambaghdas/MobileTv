package train.apitrainclient.plugins;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * ResourceUtils used to access resources. Checks API level here. This class used to make code simple in other
 * components (accessing this class)
 */

public class ResourceUtils {

    public static Drawable getDrawableByName(Context context, String resourceName){
        int resourceId = context.getResources().getIdentifier(resourceName,"drawable",context.getPackageName());
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getDrawable(resourceId);
        } else {
            drawable = context.getResources().getDrawable(resourceId);
        }
        return drawable;
    }

    public static Drawable getDrawableById(Context context, int resourceId){
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getDrawable(resourceId);
        } else {
            drawable = context.getResources().getDrawable(resourceId);
        }
        return drawable;
    }

    public static int getColorById(Context context, int resourceId){
        int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color = context.getColor(resourceId);
        } else {
            color = context.getResources().getColor(resourceId);
        }
        return color;
    }

    public static String getStringByName(Context context, String resourceName){
        int resourceId = context.getResources().getIdentifier(resourceName,"drawable",context.getPackageName());
        return context.getString(resourceId);
    }
}

package com.pttrackershared.plugins;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * NetworkUtils checks whether internet connection is available or not
 */

public class NetworkUtils {

    private static ConnectivityManager connectivityManager_ = null;

    public static boolean IsNetworkAvailable(Context context) {

        if (connectivityManager_ == null) {
            connectivityManager_ = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }

        NetworkInfo networkInfo_ = connectivityManager_.getActiveNetworkInfo();
        return (networkInfo_ != null) && (networkInfo_.isConnected());
    }
}

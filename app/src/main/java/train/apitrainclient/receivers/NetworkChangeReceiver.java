package train.apitrainclient.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import train.apitrainclient.utils.NetworkUtils;


/**
 * NetworkChangeReceiver receives network state changes to start/stop syncing process
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        if (NetworkUtils.IsNetworkAvailable(context)) {
        } else {
        }
    }
}

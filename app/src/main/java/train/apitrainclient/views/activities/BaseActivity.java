package train.apitrainclient.views.activities;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import train.apitrainclient.heartRate.BluetoothLeService;
import train.apitrainclient.heartRate.SampleGattAttributes;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.Workout;

import train.apitrainclient.plugins.LocaleHelper;
import train.apitrainclient.services.DialogUtils;


/**
 * BaseActivity manages basic operations needed for an activity. Any common logic for all activities can be implemented here.
 */

public class BaseActivity extends AppCompatActivity {

    //region View References
    //endregion

    //region Other Variables
    public Context context;
    //    public SyncProgressView progressView;
    public static android.app.AlertDialog dialog;
    public static String heartRate = "";
    public static Workout workout;
    public static boolean networkConnection;
    public static boolean synced;
    public static boolean isExerciseStarted;
    public static boolean isExerciseRunning;
    public static Date startExerciseTime;
    public static boolean dateSet;
    public static BaseActivity activity;
    private int minBpm = -1;
    private boolean shouldReadBpm;
    private int maxBpm;
    private TrainingLog currentTrainingLog;
    public static int countDown;
    private ExpandableListView mGattServicesList;
    private BluetoothLeService mBluetoothLeService;
    public String deviceAddress = "";
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private boolean mConnected = false;

    //region Controllers Variables
    //endregion

    //region Overridden Methods from AppCompatActivity Base Class


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //region necessary operations to do after activity creation
        preStart();
        initData();
        initControllers();
        initViews();
        initListeners();
        postStart();
        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("showDialog") && this instanceof HomeActivity) {
            showDialog();
        }
        //endregion
    }

    private void showDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getBaseContext());
        builder1.setMessage("Error is occurring due to data inconsistency on server.");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder1.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }

    public static BaseActivity getInstance(){return activity;}


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    /**
     * Perform business logic on activity paused.
     */
    @Override
    protected void onPause() {
        super.onPause();
        activity = this;
        unregisterReceiver(mGattUpdateReceiver);

    }

    /**
     * Perform business logic on activity resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(deviceAddress);
            Log.d("Fail", "Connect request result=" + result);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * Can handle permission results at this single point in this base class
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    //endregion

    //region Basic Methods needed for Activity flow

    /**
     * Performs all operations right after creation of activity
     */
    public void preStart() {
        context = this;
//        progressView = new SyncProgressView(context);
//        if (dialog == null)
//            dialog = new android.app.AlertDialog.Builder(context.getApplicationContext(), android.R.style.Theme_DeviceDefault_Light_Dialog)
//                    .setTitle("Sync Stats")
//                    .setMessage("Last Sync Stats")
//                    .setView(progressView)
//                    .setCancelable(false)
//                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//                        }
//                    })
//                    .create();
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e("Fail", "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(deviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    private void displayData(String data) {
        if (data != null) {
            heartRate = data;
        }
    }

    private void updateConnectionState(String resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //mConnectionState.setText(resourceId);
            }
        });
    }

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                updateConnectionState("Connected");
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                updateConnectionState("Disconnected");
                invalidateOptionsMenu();
                clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                Log.v("received", "data");
                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };

    private void clearUI() {
        heartRate = "";
    }

    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = "Unknown service";
        String unknownCharaString = "Unknown char";
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.

        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();

            currentServiceData.put(
                    "List", SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put("UUID", uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();

                String gattInfo = SampleGattAttributes.lookup(uuid, unknownCharaString);
                if (gattInfo == "Heart Rate Measurement") {
                    currentCharaData.put(
                            "List", SampleGattAttributes.lookup(uuid, unknownCharaString));
                    currentCharaData.put("UUID", uuid);
                    Log.v("loop", uuid);
                    mBluetoothLeService.setCharacteristicNotification(gattCharacteristic, true);
                }


                gattCharacteristicGroupData.add(currentCharaData);

            }
            mGattCharacteristics.add(charas);

            gattCharacteristicData.add(gattCharacteristicGroupData);
        }

        /**
         SimpleExpandableListAdapter gattServiceAdapter = new SimpleExpandableListAdapter(
         this,
         gattServiceData,
         android.R.layout.simple_expandable_list_item_2,
         new String[] {LIST_NAME, LIST_UUID},
         new int[] { android.R.id.text1, android.R.id.text2 },
         gattCharacteristicData,
         android.R.layout.simple_expandable_list_item_2,
         new String[] {LIST_NAME, LIST_UUID},
         new int[] { android.R.id.text1, android.R.id.text2 }
         );
         mGattServicesList.setAdapter(gattServiceAdapter);
         **/
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    public void startBluetoothDevice(){
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    /**
     * Initializes independently all data(not dependent on view or controller) variables to be used in this activity.
     */
    public void initData() {
    }

    /**
     * Initializes independently all controllers (not dependent on view) to be used in this activity
     */
    public void initControllers() {
    }

    /**
     * Initializes view references from xml view and programmatically created view.
     */
    public void initViews() {
        ButterKnife.bind(this);
    }

    /**
     * Implements listeners views actions and other items related to this activity.
     * Anonymous implementation of a listener is preferred as this helps in debugging
     */
    public void initListeners() {
    }

    /**
     * Performs activity specific business logic here after complete initialization of data, controllers and views.
     */
    public void postStart() {
//        KeyboardUtils.SetupHidableKeyboard(context);
    }

    @Override
    protected void onDestroy() {
        DialogUtils.HideDialog();
        super.onDestroy();
    }

    public void showSyncingProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                if (dialog != null && !(BaseActivity.this instanceof SettingsActivity)) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
//                    } else
//                        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                    dialog.show();
//                }
            }
        });

    }

    public static void setExerciseDate(){
        if (!dateSet){
            dateSet = true;
            startExerciseTime = new Date();
        }
    }

    public void setShouldReadBpm(boolean shouldReadBpm) {
        this.shouldReadBpm = shouldReadBpm;
    }

    public void makeHeartRateValuesDefault() {
        minBpm = -1;
        maxBpm = 0;
    }

    public int getMinBpm() {
        return minBpm;
    }

    public int getMaxBpm() {
        return maxBpm;
    }

    public int getAvgBpm() {
        return (minBpm + maxBpm) / 2;
    }

    public TrainingLog getCurrentTrainingLog() {
        return currentTrainingLog;
    }

    public void setCurrentTrainingLog(TrainingLog currentTrainingLog) {
        this.currentTrainingLog = currentTrainingLog;
    }

}
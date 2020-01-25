package train.apitrainclient.heartRate;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import train.apitrainclient.R;
import train.apitrainclient.listeners.OnDeviceSelectedListener;

public class DeviceScanActivity extends AppCompatActivity implements BluetoothAdapter.LeScanCallback {
    private RecyclerView recyclerViewDevices;
    private ArrayList<BluetoothDevice> mLeDevices = new ArrayList<>();
    private boolean mScanning;
    private Handler mHandler;
    BluetoothDevice newDevice;

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_ENABLE_LOCATIOM = 2;
    private static final long SCAN_PERIOD = 500;

    private LeDeviceListAdapter leDeviceListAdapter;
    private BluetoothAdapter bluetoothAdapter;
    private Scanner scanner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_scan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        mHandler = new Handler();

        recyclerViewDevices = (RecyclerView) findViewById(R.id.rvDevices);

        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "Not supported", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Not supported", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (scanner == null || !scanner.isScanning()) {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(null);
        } else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(
                    R.layout.actionbar_indeterminate_progress);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                leDeviceListAdapter.clear();
                if (scanner == null) {
                    scanner = new Scanner(bluetoothAdapter, DeviceScanActivity.this);
                    scanner.startScanning();

                    invalidateOptionsMenu();
                }
                break;
            case R.id.menu_stop:
                if (scanner != null) {
                    scanner.stopScanning();
                    scanner = null;

                    invalidateOptionsMenu();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.

        if (!bluetoothAdapter.isEnabled()) {
            final Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            return;
        }

        init();


    }

    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private void init() {
        if (leDeviceListAdapter == null) {
            leDeviceListAdapter = new LeDeviceListAdapter(this, new OnDeviceSelectedListener() {
                @Override
                public void OnDeviceSelected(BluetoothDevice device) {
                    final Intent intent = new Intent();
                    intent.putExtra("DeviceName", device.getName());
                    intent.putExtra("DeviceAdress", device.getAddress());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
            recyclerViewDevices.setHasFixedSize(true);
            recyclerViewDevices.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewDevices.setAdapter(leDeviceListAdapter);
        }

        if (scanner == null) {
            scanner = new Scanner(bluetoothAdapter, DeviceScanActivity.this);
            scanner.startScanning();
        }

        invalidateOptionsMenu();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_CANCELED) {
                finish();
            } else {
                init();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (scanner != null) {
            scanner.stopScanning();
            scanner = null;
        }
    }


    private static class Scanner extends Thread {
        private final BluetoothAdapter bluetoothAdapter;
        private final BluetoothAdapter.LeScanCallback mLeScanCallback;

        private volatile boolean isScanning = false;

        Scanner(BluetoothAdapter adapter, BluetoothAdapter.LeScanCallback callback) {
            bluetoothAdapter = adapter;
            mLeScanCallback = callback;
        }

        public boolean isScanning() {
            return isScanning;
        }

        public void startScanning() {
            synchronized (this) {
                isScanning = true;
                start();
            }
        }

        public void stopScanning() {
            synchronized (this) {
                isScanning = false;
                bluetoothAdapter.stopLeScan(mLeScanCallback);
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    synchronized (this) {
                        if (!isScanning)
                            break;

                        bluetoothAdapter.startLeScan(mLeScanCallback);
                    }

                    sleep(SCAN_PERIOD);

                    synchronized (this) {
                        bluetoothAdapter.stopLeScan(mLeScanCallback);
                    }
                }
            } catch (InterruptedException ignore) {
            } finally {
                bluetoothAdapter.stopLeScan(mLeScanCallback);
            }
        }
    }


    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        newDevice = device;
        if (!mLeDevices.contains(device))
        mLeDevices.add(newDevice);

       leDeviceListAdapter.setITems(mLeDevices);

    }

    public class LeDeviceListAdapter extends RecyclerView.Adapter<LeDeviceListAdapter.ViewHolder> {

        private ArrayList<BluetoothDevice> mLeDevices;
        Context context;
        private OnDeviceSelectedListener onDeviceSelectedListener;

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.device_name)
            TextView deviceName;

            @BindView(R.id.device_address)
            TextView deviceAddress;

            @BindView(R.id.deviceLayout)
            LinearLayout deviceLayout;

            View rootView;


            public ViewHolder(View view) {
                super(view);
                rootView = view;
                ButterKnife.bind(this, view);
            }
        }

        public LeDeviceListAdapter(Context context,OnDeviceSelectedListener onDeviceSelectedListener) {
            this.context = context;
            this.onDeviceSelectedListener = onDeviceSelectedListener;
        }

        public void setITems(ArrayList<BluetoothDevice> mLeDevices) {
            this.mLeDevices = mLeDevices;
            notifyDataSetChanged();
        }

        public void addDevice(BluetoothDevice device) {
            if (!mLeDevices.contains(device)) {
                mLeDevices.add(device);
            }
        }

        public BluetoothDevice getDevice(int position) {
            return mLeDevices.get(position);
        }

        public void clear() {
            if (mLeDevices != null)
                mLeDevices.clear();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_device, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final BluetoothDevice device = mLeDevices.get(position);
            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0)
                holder.deviceName.setText(deviceName);
            else
                holder.deviceName.setText("Unknown device");
            holder.deviceAddress.setText(device.getAddress());


            holder.deviceLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeviceSelectedListener.OnDeviceSelected(device);
                }
            });


        }

        @Override
        public int getItemCount() {
            if (mLeDevices != null && mLeDevices.size() > 0)
                return mLeDevices.size();
            else
                return 0;
        }
    }
}
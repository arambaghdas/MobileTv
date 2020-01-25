package com.pttracker.trainingaid.services;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.pttracker.trainingaid.eventbus.HearRateChangedEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * This is a background service that is bounded to an activity through service connection and
 * this service keeps on listening to the heart rate sensor
 */

public class HeartbeatService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    public static int currentValue = 0;
    private static final String LOG_TAG = "MyHeart";
    private IBinder binder = new HeartbeatServiceBinder();
    private OnChangeListener onChangeListener;
    public static boolean IS_Bounded=false;

    // interface to pass a heartbeat value to the implementing class
    public interface OnChangeListener {
        void onValueChanged(int newValue);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        HeartbeatService.IS_Bounded = false;
        return super.onUnbind(intent);

    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        HeartbeatService.IS_Bounded = true;
    }

    /**
     * Binder for this service. The binding activity passes a listener we send the heartbeat to.
     */
    public class HeartbeatServiceBinder extends Binder {
        public void setChangeListener(OnChangeListener listener) {
            onChangeListener = listener;
            // return currently known value
            listener.onValueChanged(currentValue);
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        HeartbeatService.IS_Bounded = true;
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // register us as a sensor listener
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        // delay SENSOR_DELAY_UI is sufficient
        mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_UI);
        Log.d(LOG_TAG, " sensor registered");

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
        Log.d(LOG_TAG, " sensor unregistered");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // is this a heartbeat event and does it have data?
        if (sensorEvent.sensor.getType() == Sensor.TYPE_HEART_RATE && sensorEvent.values.length > 0) {
            int newValue = (int) sensorEvent.values[0];
            if (currentValue != newValue && newValue >= 0) {
                // save the new value
                currentValue = newValue;
                Log.d(LOG_TAG, "sending new value to listener: " + newValue);

                HearRateChangedEvent hearRateChangedEvent = new HearRateChangedEvent();
                hearRateChangedEvent.setBpm(newValue);
                EventBus.getDefault().post(hearRateChangedEvent);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
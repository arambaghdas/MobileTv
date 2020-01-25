package com.pttracker.services;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

import com.pttracker.models.eventbus.HearRateChangedEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * This is a background service that is bounded to an activity through service connection and
 * this service keeps on listening to the heart rate sensor
 */

public class HeartbeatService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private SensorManager mSensorManagerStep;
    private int currentValue = 0;
    private int currentValueSteps = 0;
    private static final String LOG_TAG = "MyHeart";
//    private IBinder binder = new HeartbeatServiceBinder();
//    private OnChangeListener onChangeListener;
//    public static boolean IS_Bounded=false;
    // interface to pass a heartbeat value to the implementing class
//    public interface OnChangeListener {
//        void onValueChanged(int newValue);
//    }

//    @Override
//    public boolean onUnbind(Intent intent) {
//        HeartbeatService.IS_Bounded = false;
//        return super.onUnbind(intent);
//
//    }

//    @Override
//    public void onRebind(Intent intent) {
//        super.onRebind(intent);
//        HeartbeatService.IS_Bounded = true;
//    }

    /**
     * Binder for this service. The binding activity passes a listener we send the heartbeat to.
     */
//    public class HeartbeatServiceBinder extends Binder {
//        public void setChangeListener(OnChangeListener listener) {
//            onChangeListener = listener;
//            // return currently known value
//            listener.onValueChanged(currentValue);
//        }
//    }


//    @Override
//    public IBinder onBind(Intent intent) {
//        HeartbeatService.IS_Bounded = true;
//        return binder;
//    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // register us as a sensor listener
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManagerStep = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        Sensor mSensorSteps = mSensorManagerStep.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        // delay SENSOR_DELAY_UI is sufficient
        mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_UI);
        mSensorManagerStep.registerListener(this, mSensorSteps, SensorManager.SENSOR_DELAY_UI);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
        mSensorManagerStep.unregisterListener(this);
        Log.d(LOG_TAG, " sensor unregistered");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
                hearRateChangedEvent.setType(HearRateChangedEvent.TYPE_HEART);
                EventBus.getDefault().post(hearRateChangedEvent);
            }
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER && sensorEvent.values.length > 0) {
            int newValue = (int) sensorEvent.values[0];
            if (currentValueSteps != newValue && newValue >= 0) {
                // save the new value
                currentValueSteps = newValue;
                Log.d(LOG_TAG, "sending new Steps value to listener: " + newValue);

                HearRateChangedEvent hearRateChangedEvent = new HearRateChangedEvent();
                hearRateChangedEvent.setSteps(newValue);
                hearRateChangedEvent.setType(HearRateChangedEvent.TYPE_HEART);
                EventBus.getDefault().post(hearRateChangedEvent);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

package com.pttrackershared.plugins;

import android.os.Handler;
import android.widget.TextView;

import java.util.Date;

/**
 * StopWatchUtils used to show incrementing timer on a textview
 */

public class StopWatchUtils {

    private Handler handler;
    private TextView tvTimer;
    private Date startTime;
    private int shortInterval = 500;
    private int numberOfTimeItems;
    private int offset;

    public StopWatchUtils(Date startTime, TextView tvTimer){
        this.startTime = startTime;
        this.tvTimer = tvTimer;
        handler = new Handler();
        offset = 0;
        numberOfTimeItems = 3;
    }

    public StopWatchUtils(Date startTime, TextView tvTimer, int numberOfTimeItems, int offset){
        this.startTime = startTime;
        this.tvTimer = tvTimer;
        handler = new Handler();
        this.offset = offset;
        this.numberOfTimeItems = numberOfTimeItems;
    }

    public void start(){
        handler.removeCallbacks(timerRunnable);
        handler.post(timerRunnable);
    }

    public void stop(){
        handler.removeCallbacks(timerRunnable);
    }

    public void reset(){
        handler.removeCallbacks(timerRunnable);
        tvTimer.setText(TimeUtils.getTimeString(0, numberOfTimeItems));
    }

    Runnable timerRunnable = new  Runnable() {
        @Override
        public void run() {
            long currentTimeMillis = System.currentTimeMillis();
            long startTimeMillis = startTime.getTime();
            int seconds = (int) ((currentTimeMillis - startTimeMillis) / 1000);
            tvTimer.setText(TimeUtils.getTimeString(seconds+offset, numberOfTimeItems));
            handler.postDelayed(this, shortInterval);
        }
    };
}

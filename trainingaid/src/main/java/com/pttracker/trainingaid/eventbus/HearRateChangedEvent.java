package com.pttracker.trainingaid.eventbus;

/**
 * RoutineDataReceivedEvent is posted whenever wear receives routine data from mobile
 */

public class HearRateChangedEvent {
    private int bpm;

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }
}

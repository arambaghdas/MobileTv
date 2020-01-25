package com.pttracker.trainingaid.eventbus;

/**
 * This event is broadcasted whenever a Routine Data is fetched from mobile
 */

public class RoutineDataFetchingEvent {
    public RoutineDataFetchingEvent(boolean isFetched) {
        this.isFetched = isFetched;
    }

    public boolean isFetched() {
        return isFetched;
    }

    public void setFetched(boolean fetched) {
        isFetched = fetched;
    }

    boolean isFetched;//false = is busy
}

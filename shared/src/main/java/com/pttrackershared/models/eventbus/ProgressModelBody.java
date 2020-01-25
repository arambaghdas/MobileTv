package com.pttrackershared.models.eventbus;

import java.io.Serializable;

public class ProgressModelBody implements Serializable {

    private String client_id;
    private int calendar_week;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public int getCalendar_week() {
        return calendar_week;
    }

    public void setCalendar_week(int calendar_week) {
        this.calendar_week = calendar_week;
    }
}

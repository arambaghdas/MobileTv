package com.pttrackershared.models.eventbus;

/**
 * @author Atif Ali
 * @since September 13, 2017 2:45 PM
 * PendingMessageEvent is posted to send message to app components to node.
 */

public class PendingMessageEvent {
    private String path;
    private String data;

    private boolean isDataTransported;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isDataTransported() {
        return isDataTransported;
    }

    public void setDataTransported(boolean dataTransported) {
        isDataTransported = dataTransported;
    }
}

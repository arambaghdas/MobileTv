package com.pttrackershared.datamanagers;

import android.content.Context;

/**
 * Manages the connection among nodes
 */
public class ConnectionDataManager {

    private static ConnectionDataManager mInstance = null;
    private Context context;
    private String connectedNodeName;

    private ConnectionDataManager(Context context) {
        this.context = context;
        connectedNodeName = null;
    }

    public static ConnectionDataManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ConnectionDataManager(context);
        }
        return mInstance;
    }

    /**
     * Destroys mInstance from memory. As mInstance is a static object so this object will exist
     * even all activities are destroyed.
     */
    public void release() {
        if (mInstance != null) {
            mInstance = null;
        }
    }

    //region User Related Actions

    //endregion

    //region All Interfaces Provided By this DataManager class

    //endregion

    //region Data Storage Related Functions

    public String getConnectedNodeName() {
        return connectedNodeName;
    }

    public void setConnectedNodeName(String connectedNodeName) {
        this.connectedNodeName = connectedNodeName;
    }

    //endregion
}
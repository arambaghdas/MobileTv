package com.pttrackershared.services;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;
import com.pttrackershared.datamanagers.ConnectionDataManager;
import com.pttrackershared.models.eventbus.NodeConnectedEvent;
import com.pttrackershared.models.eventbus.PendingMessageEvent;
import com.pttrackershared.plugins.LoggerUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Set;

/**
 * MessagesListenerService is a shared service and handles communication with both nodes (phone and wear)
 * Sends message to other node by handling eventbus event register for object PendingMessageEvent
 * Posts NodeConnectedEvent to eventbus events so that app's component can register with this event to send required messages
 */

public class MessagesListenerService extends WearableListenerService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    //region "Request Keys"
    public final static String ROUTINE_DATA_REQUEST = "RoutineDataRequest1";
    public final static String ROUTINE_DATA_REQUEST_LIVE = "RoutineDataRequestlive";
    public final static String ROUTINE_DATA_UPDATE = "RoutineDataUpdate1";
    public final static String USER_INFO_REQUEST = "UserInfoRequest1";
    public final static String LOGGED_OUT_UPDATE = "LoggedOutUpdate1";
    public final static String LOG_IN_UPDATE = "LogInUpdate1";
    public final static String ROUTINE_DATA_PARTIAL_UPDATE = "RoutineDataPartialUpdate";
    public final static String EXERCISES_IMAGES_REQUEST = "ExercisesImagesRequest";
    public final static String EXERCISES_IMAGES_PARTIAL_UPDATE = "ExercisesImagesPartialUpdate";
    public final static String TRAINING_LOGS_DATA_UPDATE = "TrainingLogDataUpdate";
    public final static String TRAINING_LOGS_PARTIAL_UPDATE = "TrainingLogPartialUpdate";
    public final static String TRAINING_LOGS_EXERCISE_DATA = "TrainingLogExerciseData";
    //endregion

    private static final String CAPABILITY_NAME = "mobile";

    private Node mNode; // the connected device to send the message to
    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();

        //Connect the GoogleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        EventBus.getDefault().register(this);
        mGoogleApiClient.connect();
    }

    private Node pickBestNodeId(Set<Node> nodes) {
        Node bestNode = null;
        // Find a nearby node or pick one arbitrarily
        for (Node node : nodes) {
            if (node.isNearby()) {
                return node;
            }
        }
        return bestNode;
    }

    private void updateCapability(CapabilityInfo capabilityInfo) {
        Set<Node> connectedNodes = capabilityInfo.getNodes();
        mNode = pickBestNodeId(connectedNodes);
        if (mNode != null) {
            postNodeConnected();
            ConnectionDataManager.getInstance(this).setConnectedNodeName(mNode.getDisplayName());
        } else {
            ConnectionDataManager.getInstance(this).setConnectedNodeName(null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //region Business Logic Specific to this Service

    /**
     * Fetches the connected node
     */
    private void resolveNode() {
        mNode = null;
        Wearable.CapabilityApi.getCapability(
                mGoogleApiClient, CAPABILITY_NAME,
                CapabilityApi.FILTER_REACHABLE).setResultCallback(new ResultCallback<CapabilityApi.GetCapabilityResult>() {
            @Override
            public void onResult(@NonNull CapabilityApi.GetCapabilityResult result) {
                if (result.getCapability() == null) {
                    LoggerUtils.d("we detected no capability");
                } else {
                    updateCapability(result.getCapability());
                    LoggerUtils.d("we detected a capability");
                }
            }
        });
    }

    private void postNodeConnected() {
        EventBus.getDefault().post(new NodeConnectedEvent());
    }

    /**
     * Send message to mobile wear 
     */
    private void sendMessage(String path, String strData) {
        byte[] data = null;
        if (strData != null) {
            data = strData.getBytes();
        }

        if (mNode != null && mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                Wearable.MessageApi.sendMessage(mGoogleApiClient, mNode.getId(), path, data);
        }
    }

    public void showToast(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MessagesListenerService.this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
    //endregion

    //region Callback Methods
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        LoggerUtils.d("Path: " + messageEvent.getPath() + " data " + new String(messageEvent.getData()));
    }

    @Override
    public void onConnected(Bundle bundle) {
        resolveNode();

        CapabilityApi.CapabilityListener capabilityListener =
                new CapabilityApi.CapabilityListener() {
                    @Override
                    public void onCapabilityChanged(CapabilityInfo capabilityInfo) {
                        updateCapability(capabilityInfo);
                    }
                };
        Wearable.CapabilityApi.addCapabilityListener(mGoogleApiClient, capabilityListener, CAPABILITY_NAME);
    }

    @Override
    public void onConnectionSuspended(int i) {
        LoggerUtils.d("Connection Suspend");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        LoggerUtils.d("Connection Failed");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPendingMessageEvent(PendingMessageEvent pendingMessageEvent) {
        sendMessage(pendingMessageEvent.getPath(), pendingMessageEvent.getData());
    }
    //endregion
}

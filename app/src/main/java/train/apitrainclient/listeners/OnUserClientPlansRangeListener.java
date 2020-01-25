package train.apitrainclient.listeners;

import com.pttrackershared.models.eventbus.ClientPlanRange;

public interface OnUserClientPlansRangeListener {
    void onSuccess(ClientPlanRange object);

    void onFailure(int errorCode, String errorMessage);
}

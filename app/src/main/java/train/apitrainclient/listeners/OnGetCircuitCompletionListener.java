package train.apitrainclient.listeners;


import com.pttrackershared.models.eventbus.Circuit;

import java.util.List;

public interface OnGetCircuitCompletionListener {
    void onSuccess(List<Circuit> circuitList);

    void onFailure(int errorCode, String errorMessage);
}


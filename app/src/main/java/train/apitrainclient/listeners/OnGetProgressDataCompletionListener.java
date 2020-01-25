package train.apitrainclient.listeners;

import com.pttrackershared.models.eventbus.FequsModel;
import com.pttrackershared.models.eventbus.ProgressModel;

import java.util.List;

public interface OnGetProgressDataCompletionListener {
    void onSuccess(ProgressModel progressModel);

    void onFailure(int errorCode, String errorMessage);
}

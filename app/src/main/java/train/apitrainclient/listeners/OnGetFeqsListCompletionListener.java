package train.apitrainclient.listeners;

import java.util.List;

import com.pttrackershared.models.eventbus.FequsModel;

public interface OnGetFeqsListCompletionListener {
    void onSuccess(List<FequsModel> fequsList);

    void onFailure(int errorCode, String errorMessage);
}

package train.apitrainclient.listeners;

import java.util.List;

import com.pttrackershared.models.eventbus.GraphInfo;
import com.pttrackershared.models.eventbus.GraphInfoItem;

public interface OnGetGraphInfoCompletionListener {
    void onSuccess(GraphInfo graphInfo, List<GraphInfoItem> graphInfoItemList);

    void onFailure(int errorCode, String errorMessage);
}

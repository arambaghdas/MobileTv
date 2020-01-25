package train.apitrainclient.listeners;

import com.pttrackershared.models.eventbus.User;

public interface OnLoginViaEmailCompletionListener {
    void onSuccess(User user, String accessToken);

    void onFailure(int errorCode, String errorMessage);
}

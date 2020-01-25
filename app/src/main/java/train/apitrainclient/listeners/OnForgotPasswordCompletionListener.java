package train.apitrainclient.listeners;

public interface OnForgotPasswordCompletionListener {
    void onSuccess(Object message);

    void onFailure(int errorCode, String errorMessage);
}

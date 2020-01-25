package train.apitrainclient.listeners;

public interface OnUpdateUserAccountCompletionListener {
    void onSuccess(String  image);

    void onFailure(int errorCode, String errorMessage);
}

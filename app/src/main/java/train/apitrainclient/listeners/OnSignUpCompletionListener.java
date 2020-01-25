package train.apitrainclient.listeners;

public interface OnSignUpCompletionListener {
        void onSuccess(Object message);
        void onFailure(int errorCode, String errorMessage);
}

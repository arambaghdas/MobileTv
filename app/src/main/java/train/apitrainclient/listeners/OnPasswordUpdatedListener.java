package train.apitrainclient.listeners;

public interface OnPasswordUpdatedListener {

        void onSuccess(Object message);

        void onFailure(int errorCode, String errorMessage);
}

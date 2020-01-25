package train.apitrainclient.listeners;

public interface OnUserPackageUpdateListener {
        void onSuccess(Object object);

        void onFailure(int errorCode, String errorMessage);

}

package iunetworks.mobiletv.model;

public class AppDataManager {

    private AuthResponse authResponse;

    public AppDataManager(AuthResponse authResponse) {
        this.authResponse = authResponse;
    }

    public void sendLoginRequest(String email, String password) {
        if (email.equals("arambaghdas@gmail.com") && password.equals("Test1234")) {
            authResponse.onResponseSuccess();
        } else {
            authResponse.onResponseError();
        }
    }
}

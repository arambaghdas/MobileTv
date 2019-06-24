package iunetworks.mobiletv.ui.login;

public interface LoginView {
    void showEmailError(int resId);
    void showPasswordError(int resId);
    void enableLogin();
    void disableLogin();
    void hideEmailError();
    void hidePasswordError();
    void loginSuccess();
    void loginFail();
    String getEmail();
    String getPassword();
}

package iunetworks.mobiletv.presenter;

import iunetworks.mobiletv.model.AppDataManager;
import iunetworks.mobiletv.model.AuthResponse;
import iunetworks.mobiletv.ui.login.LoginView;
import iunetworks.mobiletv.R;

public class SignInPresenter implements AuthResponse {

    private LoginView loginView;
    private AppDataManager appDataManager;

    public SignInPresenter(LoginView loginView) {
        this.loginView = loginView;
        appDataManager = new AppDataManager(this);
    }

    public void checkLoginState(Boolean state) {
        if (state) {
            loginView.enableLogin();
        } else {
            loginView.disableLogin();
            loginView.hideEmailError();
            loginView.hidePasswordError();
        }
    }
    public void checkLoginState() {
        checkLoginState((!loginView.getEmail().isEmpty() && !loginView.getPassword().isEmpty()));
    }

    public void handleLogInClick() {
        String email = loginView.getEmail();
        String password = loginView.getPassword();
        boolean result = validateEmail(email);
        result = validatePassword(password) && result;

        if (result)
            appDataManager.sendLoginRequest(email, password);

    }

    private boolean validateEmail(String email) {
       String emailReg = "^([-!#-'*+/-9=?A-Z^-~]+(\\.[-!#-'*+/-9=?A-Z^-~]+)*|\"(\\[]!#-[^-~ \\t]|(\\\\[\\t -~]))+\")@[0-9A-Za-z]([0-9A-Za-z-]{0,61}[0-9A-Za-z])?(\\.[0-9A-Za-z]([0-9A-Za-z-]{0,61}[0-9A-Za-z])?)+$";
        if (email.matches(emailReg)) {
            loginView.hideEmailError();
            return true;
        } else  {
            loginView.showEmailError(R.string.email_validation_error);
            return false;
        }
    }

    private boolean validatePassword(String password) {
        boolean all = password.matches("^[a-zA-Z0-9!@#$%^&*]+$");
        boolean small = password.matches("(?s).*[a-z].*");
        boolean number = password.matches("(?s).*[0-9].*");
        boolean result = all && small && number;

        if (result)
            loginView.hidePasswordError();
        else
            loginView.showPasswordError(R.string.password_validation_error);

        return result;
    }

    @Override
    public void onResponseError() {
        loginView.loginFail();
    }

    @Override
    public void onResponseSuccess() {
        loginView.loginSuccess();
    }
}

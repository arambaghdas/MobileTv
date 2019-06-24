package iunetworks.mobiletv.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import iunetworks.mobiletv.R;
import iunetworks.mobiletv.ui.login.LoginView;

public class SignInPresenterTest {

    @org.mockito.Mock
    private LoginView loginView;
    private SignInPresenter loginPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        loginPresenter = new SignInPresenter(loginView);
    }

    @Test
    public void enableLogin() {
        Mockito.when(loginView.getEmail()).thenReturn("arambaghdas@gamil.com");
        Mockito.when(loginView.getPassword()).thenReturn("Test1234");
        loginPresenter.checkLoginState();
        Mockito.verify(loginView).enableLogin();
    }

    @Test
    public void disableLoginEmailPasswordAreEmpty() {
        Mockito.when(loginView.getEmail()).thenReturn("");
        Mockito.when(loginView.getPassword()).thenReturn("");
        loginPresenter.checkLoginState();
        Mockito.verify(loginView).disableLogin();
    }

    @Test
    public void disableLoginEmailIsEmpty() {
        Mockito.when(loginView.getEmail()).thenReturn("");
        Mockito.when(loginView.getPassword()).thenReturn("Test1234");
        loginPresenter.checkLoginState();
        Mockito.verify(loginView).disableLogin();
    }

    @Test
    public void disableLoginPasswordIsEmpty() {
        Mockito.when(loginView.getEmail()).thenReturn("arambaghdas@gmail.com");
        Mockito.when(loginView.getPassword()).thenReturn("");
        loginPresenter.checkLoginState();
        Mockito.verify(loginView).disableLogin();
    }

    @Test
    public void showEmailError() {
        Mockito.when(loginView.getEmail()).thenReturn("arambaghdas");
        Mockito.when(loginView.getPassword()).thenReturn("Test");
        loginPresenter.handleLogInClick();
        Mockito.verify(loginView).showEmailError(R.string.email_validation_error);
    }

    @Test
    public void hideEmailError() {
        Mockito.when(loginView.getEmail()).thenReturn("arambaghdas@gmail.com");
        Mockito.when(loginView.getPassword()).thenReturn("Test");
        loginPresenter.handleLogInClick();
        Mockito.verify(loginView).hideEmailError();
    }

    @Test
    public void showPasswordError() {
        Mockito.when(loginView.getEmail()).thenReturn("arambaghdas@gmail.com");
        Mockito.when(loginView.getPassword()).thenReturn("Test");
        loginPresenter.handleLogInClick();
        Mockito.verify(loginView).showPasswordError(R.string.password_validation_error);
    }

    @Test
    public void hidePasswordError() {
        Mockito.when(loginView.getEmail()).thenReturn("arambaghdas@gmail.com");
        Mockito.when(loginView.getPassword()).thenReturn("Test1234");
        loginPresenter.handleLogInClick();
        Mockito.verify(loginView).hidePasswordError();
    }

    @Test
    public void hidePasswordEmailError() {
        Mockito.when(loginView.getEmail()).thenReturn("arambaghdas@gmail.com");
        Mockito.when(loginView.getPassword()).thenReturn("Test1234");
        loginPresenter.handleLogInClick();
        Mockito.verify(loginView).hideEmailError();
        Mockito.verify(loginView).hidePasswordError();
    }

    @Test
    public void showPasswordEmailError() {
        Mockito.when(loginView.getEmail()).thenReturn("arambaghdas");
        Mockito.when(loginView.getPassword()).thenReturn("Test");
        loginPresenter.handleLogInClick();
        Mockito.verify(loginView).showEmailError(R.string.email_validation_error);
        Mockito.verify(loginView).showPasswordError(R.string.password_validation_error);
    }

    @Test
    public void loginSuccess() {
        Mockito.when(loginView.getEmail()).thenReturn("arambaghdas@gmail.com");
        Mockito.when(loginView.getPassword()).thenReturn("Test1234");
        loginPresenter.handleLogInClick();
        Mockito.verify(loginView).loginSuccess();
    }

    @Test
    public void loginFail() {
        Mockito.when(loginView.getEmail()).thenReturn("aram@gmail.com");
        Mockito.when(loginView.getPassword()).thenReturn("Test1234");
        loginPresenter.handleLogInClick();
        Mockito.verify(loginView).loginFail();
    }

}
package iunetworks.mobiletv.presenter;

import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import iunetworks.mobiletv.ui.intro.IntroView;

import static org.mockito.Mockito.verify;

public class IntroPresenterTest {

    @org.mockito.Mock
    private IntroView introView;
    @org.mockito.Mock
    private View view;
    private IntroPresenter introPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        introPresenter = new IntroPresenter(introView);
    }

    @Test
    public void showSignInScreen() {
        introPresenter.handleSignInClick(view);
        verify(introView).showSignInScreen();
    }

    @Test
    public void showSignUpScreen() {
        introPresenter.handleSignUpClick(view);
        verify(introView).showSignUpScreen();
    }
}
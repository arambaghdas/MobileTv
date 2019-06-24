package iunetworks.mobiletv.presenter;

import android.view.View;
import iunetworks.mobiletv.ui.intro.IntroView;

public class IntroPresenter  {

    private IntroView introView;

    public IntroPresenter(IntroView introView) {
        this.introView = introView;
    }

    public void handleSignInClick(View view) {
        introView.showSignInScreen();
    }

    public void handleSignUpClick(View view) {
        introView.showSignUpScreen();
    }
}

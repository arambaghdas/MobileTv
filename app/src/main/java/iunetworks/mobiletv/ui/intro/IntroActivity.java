package iunetworks.mobiletv.ui.intro;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import iunetworks.mobiletv.R;
import iunetworks.mobiletv.databinding.ActivityIntroBinding;
import iunetworks.mobiletv.presenter.IntroPresenter;
import iunetworks.mobiletv.ui.login.SignInActivity;
import iunetworks.mobiletv.ui.SignUpActivity;

public class IntroActivity extends AppCompatActivity implements IntroView {

    private IntroPresenter introPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityIntroBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_intro);
        introPresenter = new IntroPresenter(this);
        binding.setPresenter(introPresenter);
    }

    @Override
    public void showSignInScreen() {
        Intent i = new Intent(this, SignInActivity.class);
        startActivity(i);
    }

    @Override
    public void showSignUpScreen() {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }
}

package iunetworks.mobiletv.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import iunetworks.mobiletv.R;
import iunetworks.mobiletv.presenter.SignInPresenter;
import iunetworks.mobiletv.ui.MainActivity;

public class SignInActivity extends AppCompatActivity implements LoginView{

    @BindView(R.id.button_login) Button buttonLogin;
    @BindView(R.id.edit_text_email) EditText emailEditText;
    @BindView(R.id.text_view_email_error) TextView emailErrorTextView;
    @BindView(R.id.text_view_password_error) TextView passwordErrorTextView;
    @BindView(R.id.edit_text_password) EditText passwordEditText;
    private SignInPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        loginPresenter = new SignInPresenter(this);
        ButterKnife.bind(this);
        initObservables();
    }

    void initObservables() {
        Observable emailObservable = RxTextView
                .textChanges(emailEditText)
                .observeOn(AndroidSchedulers.mainThread())
                .map(CharSequence::toString)
                .map(String::isEmpty);

        Observable passwordObservable = RxTextView
                .textChanges(passwordEditText)
                .observeOn(AndroidSchedulers.mainThread())
                .map(CharSequence::toString)
                .map(String::isEmpty);

        Observable totalObservable = Observable.combineLatest(
                emailObservable,
                passwordObservable,
                (BiFunction<Boolean, Boolean, Boolean>) (o1, o2) -> !o1 && !o2);

        totalObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( o -> loginPresenter.checkLoginState((Boolean)o), throwable -> {});
    }

    @OnClick(R.id.button_login)
    public void performLogin(View view) {
            loginPresenter.handleLogInClick();
    }

    @Override
    public void showEmailError(int resId) {
        emailErrorTextView.setText(getString(resId));
    }

    @Override
    public void showPasswordError(int resId) {
        passwordErrorTextView.setText(getString(resId));
    }

    @Override
    public void enableLogin() {
        buttonLogin.setEnabled(true);
        buttonLogin.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void disableLogin() {
        buttonLogin.setEnabled(false);
        buttonLogin.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
    }

    @Override
    public void hideEmailError() {
        emailErrorTextView.setText("");
    }

    @Override
    public void hidePasswordError() {
        passwordErrorTextView.setText("");
    }

    @Override
    public void loginSuccess() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void loginFail() {
        emailErrorTextView.setText(getString(R.string.wrong_email));
        passwordErrorTextView.setText(getString(R.string.wrong_password));
    }

    @Override
    public String getEmail() {
        return emailEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordEditText.getText().toString();
    }

}

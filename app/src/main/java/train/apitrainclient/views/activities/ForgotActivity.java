package train.apitrainclient.views.activities;



import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.OnClick;
import train.apitrainclient.R;
import train.apitrainclient.listeners.OnForgotPasswordCompletionListener;
import train.apitrainclient.networks.retrofit.RestApiManager;
import train.apitrainclient.services.DialogUtils;
import train.apitrainclient.utils.StatusCodes;

/**
 * LoginActivity is shown if user has not logged in.
 * Shown fields to get email and password from user.
 */

public class ForgotActivity extends BaseActivity {

    //region View References
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;

    @BindView(R.id.et_email)
    EditText etEmail;


    @BindView(R.id.btn_forgot_password)
    Button btnSignIn;


    @Override
    public void preStart() {
        super.preStart();
        setContentView(R.layout.activity_forgot_password);
        context = this;
    }

    /**
     * Initializes independently all data(not dependent on view or controller) variables to be used in this activity.
     */
    @Override
    public void initData() {
        super.initData();
    }

    /**
     * Initializes independently all controllers (not dependent on view) to be used in this activity
     */
    @Override
    public void initControllers() {
        super.initListeners();
    }

    /**
     * Initializes view references from xml view and programmatically created view.
     */
    @Override
    public void initViews() {
        super.initViews();
    }

    /**
     * Implements listeners views actions and other items related to this activity.
     * Anonymous implementation of a listener is preferred as this helps in debugging
     */
    @Override
    public void initListeners() {
        super.initListeners();
    }

    /**
     * Performs activity specific business logic here after complete initialization of data, controllers and views.
     */
    @Override
    public void postStart() {
        super.postStart();
    }

    //endregion
    //region Business Logic Specific to this Activity
    private void performForgotPassword() {
        clearErrors();
        DialogUtils.ShowProgress(context, getString(R.string.progress_message_login));
        RestApiManager.forgotPassword(this, etEmail.getText().toString(), new OnForgotPasswordCompletionListener() {
            @Override
            public void onSuccess(Object message) {
                etEmail.setText("");
                DialogUtils.ShowSnackbarAlert(context, "Password sent by EMail");
                DialogUtils.HideDialog();
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                DialogUtils.HideDialog();
                showErrors(errorCode, errorMessage);
            }
        });
    }

    private void clearErrors() {
        tilEmail.setErrorEnabled(false);
    }

    private void showErrors(int errorCode, String errorMessage) {
        switch (errorCode) {
            case StatusCodes.ERROR_CODE_REQUIRED_EMAIL:
                tilEmail.setErrorEnabled(true);
                tilEmail.setError(errorMessage);
                break;

            case StatusCodes.ERROR_CODE_INVALID_EMAIL:
                tilEmail.setErrorEnabled(true);
                tilEmail.setError(errorMessage);
                break;

            default:
                DialogUtils.ShowSnackbarAlert(context, errorMessage);
        }
    }

    //region Callback Methods
    @OnClick(R.id.btn_forgot_password)
    public void onForgotPassword() {
        performForgotPassword();
    }


    @OnClick(R.id.loginBackButton)
    public void onForgotPasswordBackPress() {
        onBackPressed();
    }
}



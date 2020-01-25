package train.apitrainclient.views.activities;


import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.pttrackershared.models.eventbus.User;

import butterknife.BindView;
import butterknife.OnClick;
import train.apitrainclient.R;
import train.apitrainclient.listeners.OnPasswordUpdatedListener;
import train.apitrainclient.networks.retrofit.RestApiManager;
import train.apitrainclient.services.DialogUtils;
import train.apitrainclient.utils.SharedPrefManager;

/**
 * LoginActivity is shown if user has not logged in.
 * Shown fields to get email and password from user.
 */

public class UpdatePasswordActivity extends BaseActivity {

    //region View References
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;

    @BindView(R.id.til_current_password)
    TextInputLayout tilcurrentpassword;

    @BindView(R.id.til_new_password)
    TextInputLayout tilnewPassword;

    @BindView(R.id.til_confirm_new_password)
    TextInputLayout til_confirm_new_password;


    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_current_password)
    EditText etCurrentPassword;

    @BindView(R.id.et_new_password)
    EditText etNewPassword;

    @BindView(R.id.et_confirm_new_password)
    EditText et_confirm_new_password;

    @BindView(R.id.btn_update)
    Button btnupdate;

    User user;
    /**
     * Performs all operations right after creation of activity
     */
    @Override
    public void preStart() {
        super.preStart();
        setContentView(R.layout.activity_updatepassword);
        user = SharedPrefManager.getUser(UpdatePasswordActivity.this);
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


    //region Callback Methods
    @OnClick(R.id.btn_update)
    public void onSignInClicked() {

            if (!etEmail.getText().toString().isEmpty() && etEmail.getText().toString().equals(user.getClientemail())){
                if (!etCurrentPassword.getText().toString().isEmpty()){
                    if (!etNewPassword.getText().toString().isEmpty()){
                        if (!et_confirm_new_password.getText().toString().isEmpty()){
                            if (etNewPassword.getText().toString().equals(et_confirm_new_password.getText().toString())){
                                updateUserPassword();
                            }else {
                                et_confirm_new_password.setError("Password dont match");
                            }
                        }else {
                            et_confirm_new_password.setError("Confirm new password");
                        }
                    }else {
                        etNewPassword.setError("Enter new password");
                    }
                }else {
                    etCurrentPassword.setError("Wrong password");
                }
            }else {
                etEmail.setError("Wrong email address");
            }


    }

    private void updateUserPassword() {
        DialogUtils.ShowProgress(context, "Updating user account, please wait...");
        RestApiManager.updatePassword(UpdatePasswordActivity.this, user, etCurrentPassword.getText().toString(), etNewPassword.getText().toString(), new OnPasswordUpdatedListener() {
            @Override
            public void onSuccess(Object message) {
                DialogUtils.HideDialog();
                etCurrentPassword.setText("");
                etNewPassword.setText("");
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                DialogUtils.HideDialog();
                DialogUtils.ShowSnackbarAlert(context, errorMessage);
            }
        });
    }

    @OnClick(R.id.loginBackButton)
    public void onForgotPasswordBackPress() {
        onBackPressed();
    }


    //endregion
}



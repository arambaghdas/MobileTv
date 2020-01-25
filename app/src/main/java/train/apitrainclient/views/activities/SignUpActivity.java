package train.apitrainclient.views.activities;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import train.apitrainclient.R;
import train.apitrainclient.listeners.OnSignUpCompletionListener;
import train.apitrainclient.networks.retrofit.RestApiManager;
import train.apitrainclient.services.DialogUtils;

public class SignUpActivity extends BaseActivity {

    @BindColor(R.color.white)
    int whiteColor;

    @BindDrawable(R.drawable.abc_ic_ab_back_material)
    Drawable drUpArrow;

    @BindView(R.id.til_name)
    TextInputLayout tilName;

    @BindView(R.id.til_surname)
    TextInputLayout tilSurname;

    @BindView(R.id.til_email)
    TextInputLayout tilEmail;

    @BindView(R.id.til_password)
    TextInputLayout tilPassword;

    @BindView(R.id.til_confirm_password)
    TextInputLayout tilConfirmPassword;


    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.et_surname)
    EditText etSurname;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_mobile)
    EditText etMobile;

    @BindView(R.id.til_mobile)
    TextInputLayout tilMobile;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;


    Spinner spinnerTypeOf_Membership;
    int selectedMember;


    @BindView(R.id.btn_Signup)
    Button btnSignup;

    @BindView(R.id.loginBackButton)
    ImageButton loginBackButton;

    CheckBox cbPasswordVisibility;


    ArrayAdapter<String> memberAdapter;

    boolean ischeckedValue = false;

    int package_item_clients = 0;
    int users_groups_clients = 0;
    int profile_users = 0;
    int package_item_users = 0;
    int package_clients = 0;
    int package_users = 0;

    /**
     * Performs all operations right after creation of activity
     */
    @Override
    public void preStart() {
        super.preStart();
        setContentView(R.layout.activity_signup);
        context = this;
        setTypeOfMember();
        cbPasswordVisibility = (CheckBox) findViewById(R.id.cb_password_visibility);
        cbPasswordVisibility.setText(getText(R.string.txt_signup_agreement));

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
        setupToolbar();

        loginBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, OnboardingWithCenterAnimationActivity.class));
                finish();
            }
        });
    }

    private void setupToolbar() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //region Callback Methods
    @OnClick(R.id.btn_Signup)
    public void onSignInClicked() {
        if (checkValidateion()) {
            userSignUp();
        }
    }

    void assignDefaultValue() {
        if (selectedMember == 1) {
            package_item_clients = 11;
            users_groups_clients = 4;
            profile_users = 3;
            package_item_users = 3;
            package_clients = 1;
            package_users = 1;

        } else if (selectedMember == 2) {
            package_item_clients = 6;
            users_groups_clients = 2;
            profile_users = 1;
            package_item_users = 1;
            package_clients = 1;
            package_users = 1;

        } else if (selectedMember == 3) {
            package_item_clients = 1;
            users_groups_clients = 3;
            profile_users = 2;
            package_item_users = 2;
            package_clients = 1;
            package_users = 1;
        }

    }


    private void userSignUp() {
        assignDefaultValue();

        DialogUtils.ShowProgress(context, "Please wait...");

        RestApiManager.userSignUp(this, etName.getText().toString(),
                etSurname.getText().toString(), etEmail.getText().toString(),
                etMobile.getText().toString(),
                etPassword.getText().toString(),
                etPassword.getText().toString(),
                selectedMember,
                package_item_clients, users_groups_clients, profile_users, package_item_users, package_clients, package_users, new OnSignUpCompletionListener() {
                    @Override
                    public void onSuccess(Object message) {
                        DialogUtils.HideDialog();
                        etName.setText("");
                        etSurname.setText("");
                        etEmail.setText("");
                        etMobile.setText("");
                        etPassword.setText("");
                        etConfirmPassword.setText("");

                        DialogUtils.ShowSnackbarAlert(context, " You are IN!! Now " +
                                "you can LOGIN and start enjoying APITRAIN world");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        DialogUtils.HideDialog();
                        DialogUtils.ShowSnackbarAlert(context, errorMessage);
                    }
                });
    }


    boolean checkValidateion() {
        boolean flag = true;

        if (etName.getText().toString().length() <= 0) {
            flag = false;
            tilName.setErrorEnabled(true);
            tilName.setError("Enter Name.");
        } else if (etSurname.getText().toString().length() <= 0) {
            flag = false;
            tilSurname.setErrorEnabled(true);
            tilSurname.setError("Enter Surname.");
        } else if (etEmail.getText().toString().length() <= 0 || !isValidEmail(etEmail.getText().toString())) {
            flag = false;
            tilEmail.setErrorEnabled(true);
            tilEmail.setError("Enter Valid Email.");
        }else if (etMobile.getText().toString().length() <= 0) {
            flag = false;
            tilMobile.setErrorEnabled(true);
            tilMobile.setError("Enter Mobile.");
        }  else if (etPassword.getText().toString().length() <= 0) {
            flag = false;
            tilPassword.setErrorEnabled(true);
            tilPassword.setError("Enter Password");
        } else if (etConfirmPassword.getText().toString().length() <= 0) {
            flag = false;
            tilConfirmPassword.setErrorEnabled(true);
            tilConfirmPassword.setError("Enter Confirm Password");
        } else if (!etConfirmPassword.getText().toString().equals(etPassword.getText().toString())) {
            flag = false;
            DialogUtils.ShowSnackbarAlert(context, "Password and Confirm Password should be same");
        } else if (selectedMember == 0) {
            flag = false;
            DialogUtils.ShowSnackbarAlert(context, "Select Type Of Member");
        } else if (ischeckedValue == false) {
            flag = false;
            DialogUtils.ShowSnackbarAlert(context, "Accept the Terms of Use");
        }


        return flag;

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @OnCheckedChanged(R.id.cb_password_visibility)
    public void onPasswordVisibilityChecked(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            ischeckedValue = true;
        } else {
            ischeckedValue = false;
        }
    }

    void setTypeOfMember() {
        spinnerTypeOf_Membership = (Spinner) findViewById(R.id.spinner_type_of_membership);
        String[] memberList = {"Personal Trainer", "Public Member"};
        memberAdapter = new ArrayAdapter<String>(SignUpActivity.this, R.layout.sprinner_profile_layout, memberList);
        spinnerTypeOf_Membership.setAdapter(memberAdapter);
        memberAdapter.setDropDownViewResource(R.layout.sprinner_profile_layout);
        spinnerTypeOf_Membership.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                selectedMember = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnTextChanged(value = R.id.et_name, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void nameChanged(CharSequence text) {
        tilName.setError(null);
        tilName.setErrorEnabled(false);
    }

    @OnTextChanged(value = R.id.et_surname, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void surnameChanged(CharSequence text) {
        tilSurname.setError(null);
        tilSurname.setErrorEnabled(false);
    }

    @OnTextChanged(value = R.id.et_email, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void emailChanged(CharSequence text) {
        tilEmail.setError(null);
        tilEmail.setErrorEnabled(false);
    }

    @OnTextChanged(value = R.id.et_mobile, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void mobileChanged(CharSequence text) {
        tilMobile.setError(null);
        tilMobile.setErrorEnabled(false);
    }

    @OnTextChanged(value = R.id.et_password, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void passwordChanged(CharSequence text) {
        tilPassword.setError(null);
        tilPassword.setErrorEnabled(false);
    }

    @OnTextChanged(value = R.id.et_confirm_password, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void confirm_passwordChanged(CharSequence text) {
        tilConfirmPassword.setError(null);
        tilConfirmPassword.setErrorEnabled(false);
    }
}



package train.apitrainclient.views.fragments;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;
import com.pttrackershared.models.eventbus.Circuit;
import com.pttrackershared.models.eventbus.Exercise;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.TrainingPlan;
import com.pttrackershared.models.eventbus.TrainingPlanJsonModel;
import com.pttrackershared.models.eventbus.Workout;
import com.pttrackershared.utils.SaveUserPreferences;
import com.pttrackershared.utils.TrainingPlanPreferences;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import train.apitrainclient.R;
import train.apitrainclient.listeners.OnGetTrainingPlansCompletionListener;
import train.apitrainclient.listeners.OnLoginViaEmailCompletionListener;
import com.pttrackershared.models.eventbus.User;

import train.apitrainclient.networks.retrofit.RestApiManager;
import train.apitrainclient.receivers.NetworkStateReceiver;
import train.apitrainclient.services.DialogUtils;
import train.apitrainclient.utils.AppUtils;
import train.apitrainclient.utils.Constants;
import train.apitrainclient.utils.SharedPrefManager;
import train.apitrainclient.utils.StatusCodes;
import train.apitrainclient.utils.UserPrefManager;
import train.apitrainclient.views.activities.BaseActivity;
import train.apitrainclient.views.activities.ForgotActivity;
import train.apitrainclient.views.activities.HomeActivity;
import train.apitrainclient.views.activities.LoginActivity;

/**
 * RoutinesFragment shows list of routines assigned to current user.
 * Provides option to user to filter out workout based on routine and date.
 */

public class LoginFragment extends BaseFragment implements NetworkStateReceiver.NetworkStateReceiverListener{

    @BindView(R.id.enter_email)
    EditText etEmail;

    @BindView(R.id.enter_pass)
    EditText etPassword;

    @BindView(R.id.login_btn)
    Button btnSignIn;

    @BindView(R.id.til_email)
    TextInputLayout tilEmail;

    @BindView(R.id.til_password)
    TextInputLayout tilPassword;

    @BindView(R.id.web_link)
    TextView web_link_go;

    @BindView(R.id.check_term)
    CheckBox checkBox;

    public static String email = "";
    public static String password = "";
    public static int flagIntro = 0;
    NetworkStateReceiver networkStateReceiver;
    private User user;
    UserPrefManager userPrefManager;

    public static LoginFragment newInstance() {
        LoginFragment foodListFragments = new LoginFragment();
        return foodListFragments;
    }


    @Override
    public void preStart(LayoutInflater inflater, @Nullable ViewGroup container) {
        super.preStart(inflater, container);
        userPrefManager = new UserPrefManager(getActivity());

        if (AppUtils.bigDevice(getActivity()))
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        else
        rootView = inflater.inflate(R.layout.fragment_login_small, container, false);

        ButterKnife.bind(getActivity());

        checkLoggedInState();

        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    public void initViews() {
        ButterKnife.bind(getActivity());
        super.initViews();
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void initControllers() {
        super.initControllers();

    }

    @Override
    public void initListeners() {
        super.initListeners();
    }


    @Override
    public void postStart() {
        super.postStart();
        initCollectionAdapters();


    }

    //region Business Logic Specific to this fragment
    private void initCollectionAdapters() {
        if (!isViewAttached()) {
            return;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        networkStateReceiver.removeListener(this);
        getActivity().unregisterReceiver(networkStateReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        flagIntro = 0;
//        etEmail.setText("");
//        etPassword.setText("");
        etEmail.setTransformationMethod(null);
        etEmail.setSelection(etEmail.getText().toString().length());
    }

    @Override
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {

    }


    private void performLogin() {
        clearErrors();
        DialogUtils.ShowProgress(context, getString(R.string.progress_message_login));
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        RestApiManager.loginViaEmail(context, etEmail.getText().toString(), etPassword.getText().toString(), new OnLoginViaEmailCompletionListener() {
            @Override
            public void onSuccess(User user2, String accessToken) {
                DialogUtils.HideDialog();
                user = user2;
                userPrefManager.setLoggedIn(true);
                user2.setAccessToken(accessToken);
                userPrefManager.saveAccessToken(accessToken);
                SaveUserPreferences.addUser(user2,getActivity());
                SharedPrefManager.setUser(context, user2);

//                if (user2.getIntro() == null)
//                    user2.setIntro(0);
//
//                if (user2.getIntro() == 0){
//                    Profile_Gender_Fragment.user = user2;
//                    moveToQuestionScreen();
//                }else {
                    startActivity(new Intent(context, HomeActivity.class));
                    moveToHomeScreen();
//                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                DialogUtils.HideDialog();
                DialogUtils.ShowSnackbarAlert(context,errorMessage);
            }
        });
    }

    private void clearErrors() {
        tilEmail.setErrorEnabled(false);
        tilPassword.setErrorEnabled(false);
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

            case StatusCodes.ERROR_CODE_REQUIRED_PASSWORD:
                tilPassword.setErrorEnabled(true);
                tilPassword.setError(errorMessage);
                break;

            default:
                DialogUtils.ShowSnackbarAlert(context, errorMessage);
        }
    }

    private void moveToHomeScreen() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                RestApiManager.getTrainingPlans(context,user, new OnGetTrainingPlansCompletionListener() {
                    @Override
                    public void onSuccess(List<TrainingPlanJsonModel> trainingPlanListView) {
                        List<TrainingPlan>trainingPlanList = AppUtils.saveTrainingPlans(getActivity(),trainingPlanListView);
                        Intent i = new Intent(context, HomeActivity.class);
                        startActivity(i);
                        getActivity().finish();
                        if (BaseActivity.networkConnection && trainingPlanList.size() > 0){
                            for (int j = 0; j < trainingPlanList.size(); j++) {
                                TrainingPlan trainingPlan = trainingPlanList.get(j);
                                SharedPrefManager.setTrainingPlan(context,trainingPlan);
                                TrainingPlanPreferences.addPlan(trainingPlanList.get(0),getActivity());
                                for (int k = 0; k < trainingPlan.getWorkoutList().size(); k++) {
                                    Workout workout = trainingPlan.getWorkoutList().get(k);
                                    for (int a = 0; a < workout.getCircuitList().size(); a++) {
                                        Circuit circuit = workout.getCircuitList().get(a);
                                        for (int b = 0; b < circuit.getExerciseList().size(); b++) {
                                            Exercise exercise = TrainingLog.convertToExercise(circuit.getExerciseList().get(b));
                                            Picasso.with(context).load(Constants.IMAGE_URL_FEMALE +
                                                    exercise.getImageLink()).fetch();
                                            Picasso.with(context).load(Constants.IMAGE_URL_MALE +
                                                    exercise.getImageLink()).fetch();
                                        }
                                    }
                                }

                                if (j + 1 == trainingPlanList.size()) {
                                    getActivity().finish();
                                }
                            }
                        } else {
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getActivity().finish();
                                DialogUtils.HideDialog();
                            }
                        });
                    }
                });
                return null;
            }
        }.execute();

    }

    private void checkLoggedInState() {
        if (userPrefManager.isLoggedIn()) {
            Intent i = new Intent(context, HomeActivity.class);
            startActivity(i);
        } else {
            userPrefManager.clearPrefs(context);
        }
    }

    @OnClick(R.id.login_btn)
    public void onSignInClicked() {
        if (LoginActivity.flagIntro == 1) {
            moveToQuestionScreen();
        } else {
            if (checkBox.isChecked()) {
                performLogin();
            } else {
                Toast toast = Toast.makeText(getActivity(),"Please check Terms and Conditions", Toast.LENGTH_SHORT);
                toast.show();
                tilEmail.setError(" ");
                tilPassword.setError(" ");


            }
        }
    }

    @OnClick(R.id.check_term)
    public void checkedTerms(){
        clearErrors();
    }

    @OnCheckedChanged(R.id.show_char)
    public void onPasswordVisibilityChecked(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            etPassword.setTransformationMethod(null);
            etPassword.setSelection(etPassword.getText().toString().length());
        } else {
            etPassword.setTransformationMethod(new PasswordTransformationMethod());
            etPassword.setSelection(etPassword.getText().toString().length());
        }
    }

    @OnClick(R.id.web_link)
    public void onWebLinkClick() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.apitrain.com")));
    }

    @OnClick(R.id.txt_term)
    public void onTermClicked() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.apitrain.com/apitrain/membershipv8/index.php?page=terms-of-use&fbclid=IwAR3jjVfmnebw5zcEHINDB0WGXp9pY4yFZGQnA4Gupp5U1o5BAe5kbMDFCas")));
    }

    @OnClick(R.id.tv_forgotpass)
    public void onForgotPasswordClicked() {
        Intent intent = new Intent(getActivity(), ForgotActivity.class);
        startActivity(intent);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    void moveToQuestionScreen() {
        btnSignIn.setVisibility(View.INVISIBLE);
        Fragment fragment = Profile_Gender_Fragment.newInstance();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.view_content, fragment);
        fragmentTransaction.commit();
    }
}
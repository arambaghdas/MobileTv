package train.apitrainclient.views.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.pttrackershared.models.eventbus.Circuit;
import com.pttrackershared.models.eventbus.Exercise;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.TrainingPlanJsonModel;
import com.pttrackershared.models.eventbus.Workout;
import com.pttrackershared.utils.SaveUserPreferences;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import train.apitrainclient.R;
import train.apitrainclient.listeners.OnGetTrainingPlanListCompletionListener;
import train.apitrainclient.listeners.OnGetTrainingPlansCompletionListener;
import train.apitrainclient.listeners.OnLoginViaEmailCompletionListener;
import com.pttrackershared.models.eventbus.TrainingPlan;
import com.pttrackershared.models.eventbus.User;
import train.apitrainclient.networks.retrofit.RestApiManager;
import train.apitrainclient.services.DialogUtils;
import train.apitrainclient.utils.AppUtils;
import train.apitrainclient.utils.Constants;
import train.apitrainclient.utils.SharedPrefManager;
import train.apitrainclient.views.activities.BaseActivity;
import train.apitrainclient.views.activities.HomeActivity;

/**
 * RoutinesFragment shows list of routines assigned to current user.
 * Provides option to user to filter out workout based on routine and date.
 */

public class Profile_TrainingPlan_Fragment extends BaseFragment {

    @BindView(R.id.txt_goal_date)
    TextView txt_goal_date;

    @BindView(R.id.initialText)
    TextView initialText;

    @BindView(R.id.txt_info_yes)
    TextView txtInfo_yes;

    @BindView(R.id.txt_info_no)
    TextView txtInfo_no_1;

    @BindView(R.id.txt_info_no_2)
    TextView txtInfo_no_2;

    @BindView(R.id.layout_no)
    LinearLayout layout_no;

    @BindView(R.id.layout_yes)
    LinearLayout layout_yes;

    @BindView(R.id.dummyText4)
    TextView dummyText4;



    static int yesnoBtnSelectdValue;

    static String goalDate;
    private ArrayList<TrainingPlanJsonModel> plantList;

    int selectedPlanId;
    static int selectedFit_goal_id;

    public static Profile_TrainingPlan_Fragment newInstance(int yesnoBtnSelectdVal, int selectedFit_goal_iddd, String goalDatee) {
        yesnoBtnSelectdValue = yesnoBtnSelectdVal;
        goalDate = goalDatee;
        selectedFit_goal_id = selectedFit_goal_iddd;
        return new Profile_TrainingPlan_Fragment();
    }

    @Override
    public void preStart(LayoutInflater inflater, @Nullable ViewGroup container) {
        super.preStart(inflater, container);
        rootView = inflater.inflate(R.layout.fragment_profile_trainingplan, container, false);
        setHasOptionsMenu(false);
//        onbackprssed();
    }


    @Override
    public void initViews() {
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
        dummyText4.setText(getText(R.string.txt_questionaire_dummytext8));
        txt_goal_date.setText(txt_goal_date.getText().toString() + "\n" + goalDate);
        if (yesnoBtnSelectdValue == 0) {
            layout_yes.setVisibility(View.VISIBLE);
            loadPlanList();
            layout_no.setVisibility(View.GONE);
        } else if (yesnoBtnSelectdValue == 1) {
            layout_yes.setVisibility(View.GONE);
            layout_no.setVisibility(View.VISIBLE);
        }


    }

    @OnClick(R.id.btn_next)
    void nextButtonClicked() {
//        Intent i = new Intent(context, LoginPhoneNoActivity.class);
//        startActivity(i);
//        if (Profile_Gender_Fragment.phoneNumber.equalsIgnoreCase("")) {
            moveToHomeScreen();
//        } else {
//            performLogin();
//        }
    }


    private void loadPlanList() {
        DialogUtils.ShowProgress(context, getString(R.string.progress_message_loading_routines));
        User user = SharedPrefManager.getUser(getActivity());
        RestApiManager.getTrainingPlanList_updateuser(getActivity(), selectedFit_goal_id, new OnGetTrainingPlanListCompletionListener() {
            @Override
            public void onSuccess(List<TrainingPlanJsonModel> trainingPlanViewJsonModel) {
                plantList = new ArrayList<>();
                plantList.addAll(trainingPlanViewJsonModel);
                setTrainingPlan();
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String strDate = dateFormat.format(date);
                trainingPlanViewJsonModel.get(0).setStartDate(strDate);
                date.setDate(date.getDate() + 7);
                String finDate = dateFormat.format(date);
                trainingPlanViewJsonModel.get(0).setFinishDate(finDate);
                trainingPlanViewJsonModel.get(0).setWeek(Profile_FitnessGoal_Fragment.calendarSelectedDate);
                RestApiManager.setTrainingPlan(getActivity(), user, trainingPlanViewJsonModel.get(0), new OnGetTrainingPlansCompletionListener() {
                    @Override
                    public void onSuccess(List<TrainingPlanJsonModel> trainingPlanList) {
                        Toast.makeText(getActivity(), "Plan Updated", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
//                        Toast.makeText(getActivity(), ""+errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
                DialogUtils.HideDialog();
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                DialogUtils.HideDialog();
            }
        });
    }

    void setTrainingPlan() {
       selectedPlanId = getSelectGoalId(Profile_FitnessGoal_Fragment.selectedFitnessGoal);
       initialText.setText(initialText.getText().toString() + "\n" + Profile_FitnessGoal_Fragment.selectedFitnessGoal);
    }

    int getSelectGoalId(String name) {
        int id;
        for (int i = 0; i < plantList.size(); i++) {
            if (name.equalsIgnoreCase(plantList.get(i).getPlanName())) {
                return plantList.get(i).getUsers_plan_id();
            }
        }
        return 0;
    }

    void onbackprssed() {

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    getActivity().getSupportFragmentManager().popBackStack();
                    return true;
                }
                return false;
            }
        });

    }


    private void performLogin() {
        DialogUtils.ShowProgress(context, getString(R.string.progress_message_login));
        User user = Profile_FitnessGoal_Fragment.user;

        RestApiManager.loginViaEmail(context, LoginFragment.email, LoginFragment.password, new OnLoginViaEmailCompletionListener() {
            @Override
            public void onSuccess(User user, String accessToken) {
                DialogUtils.HideDialog();
                moveToHomeScreen();
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                DialogUtils.HideDialog();
                DialogUtils.ShowSnackbarAlert(context, errorMessage);
            }
        });
    }

    private void moveToHomeScreen() {
        User user = Profile_FitnessGoal_Fragment.user;
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
                        if (BaseActivity.networkConnection) {
                            for (int j = 0; j < trainingPlanList.size(); j++) {
                                TrainingPlan trainingPlan = trainingPlanList.get(j);
                                SharedPrefManager.setTrainingPlan(context,trainingPlan);
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


}
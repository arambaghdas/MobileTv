package train.apitrainclient.views.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;
import com.pttrackershared.models.eventbus.FitnessGoalModel;
import com.pttrackershared.models.eventbus.TrainingPlan;
import com.pttrackershared.models.eventbus.TrainingPlanJsonModel;
import com.pttrackershared.models.eventbus.User;
import com.pttrackershared.utils.SaveUserPreferences;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import train.apitrainclient.R;
import train.apitrainclient.listeners.OnFitnessGoalCompletionListener;
import train.apitrainclient.listeners.OnGetTrainingPlansCompletionListener;
import train.apitrainclient.listeners.OnLoginViaEmailCompletionListener;
import train.apitrainclient.networks.retrofit.RestApiManager;
import train.apitrainclient.services.DialogUtils;
import train.apitrainclient.utils.AppUtils;
import train.apitrainclient.utils.Constants;
import train.apitrainclient.utils.SharedPrefManager;

/**
 * RoutinesFragment shows list of routines assigned to current user.
 * Provides option to user to filter out workout based on routine and date.
 */

public class Update_FitnessGoal_Fragment extends BaseFragment {

    @BindView(R.id.planImage)
    ImageView planImage;

    @BindView(R.id.days)
    TextView days;

    @BindView(R.id.workout)
    TextView workout;
    static User user;
    Spinner spinnerfitnessgoal;
    Spinner spinnerPlans;

    int selectedPlanId;
    static int selectedFit_goal_id;
    private List<FitnessGoalModel> fitnessGoalList;
    public TrainingPlanJsonModel trainingPlan;

    public static Update_FitnessGoal_Fragment newInstance(User userr) {
        user = userr;
        return new Update_FitnessGoal_Fragment();
    }

    @Override
    public void preStart(LayoutInflater inflater, @Nullable ViewGroup container) {
        super.preStart(inflater, container);
        rootView = inflater.inflate(R.layout.update_profile_fitnessgoal, container, false);
        setHasOptionsMenu(true);
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void initData() {
        super.initData();
        fitnessGoalList = new ArrayList<>();
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
        loadFitnessGoal();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    @OnClick(R.id.btn_Next)
    void nextButtonClicked() {
        getTrainingPlans();
    }

    void setTrainingPlan() {
        selectedPlanId = getSelectGoalId(Profile_FitnessGoal_Fragment.selectedFitnessGoal);
    }


    public void getTrainingPlans(){
        setTrainingPlan();
        RestApiManager.getAllTrainingPlans(getActivity(), new OnGetTrainingPlansCompletionListener() {
            @Override
            public void onSuccess(List<TrainingPlanJsonModel> trainingPlanList) {
                int size = trainingPlanList.size();
                for (int i = 0; i < trainingPlanList.size(); i++) {
                    if (selectedFit_goal_id == trainingPlanList.get(i).getUsers_plan_id()){
                        user.setFitness_goal(selectedFit_goal_id);
                        SaveUserPreferences.addUser(user,getActivity());
                        SharedPrefManager.setUser(getActivity(),user);
                        trainingPlan = trainingPlanList.get(i);
                        Date date = Calendar.getInstance().getTime();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                        String strDate = dateFormat.format(date);
                        trainingPlan.setStartDate(strDate);
                        date.setDate(date.getDate() + 7);
                        String finDate = dateFormat.format(date);
                        trainingPlan.setFinishDate(finDate);
                        RestApiManager.setTrainingPlan(getActivity(), user, trainingPlan, new OnGetTrainingPlansCompletionListener() {
                            @Override
                            public void onSuccess(List<TrainingPlanJsonModel> trainingPlanList) {
                                Toast.makeText(getActivity(), "Plan Updated", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {
//                                Toast.makeText(getActivity(), ""+errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }


            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {

            }
        });
    }

    private void loadFitnessGoal() {
        DialogUtils.ShowProgress(context, getString(R.string.progress_message_loading_fitness_goal));
        RestApiManager.getFitnessGoal(getActivity(), new OnFitnessGoalCompletionListener() {
            @Override
            public void onSuccess(List<FitnessGoalModel> fitnessGoalModelList) {
                fitnessGoalList.clear();
                fitnessGoalList.addAll(fitnessGoalModelList);
                DialogUtils.HideDialog();
                List<String>goalNames = new ArrayList<>();
                for (int i = 0; i < fitnessGoalList.size(); i++) {
                    goalNames.add(fitnessGoalList.get(i).getGoal_description());
                }
                setFitnessGoal(goalNames);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                DialogUtils.HideDialog();
                DialogUtils.ShowSnackbarAlert(context, errorMessage);
            }
        });
    }


    void setFitnessGoal(List<String>goalNameList) {
        spinnerfitnessgoal = (Spinner) rootView.findViewById(R.id.spinner2);
        ArrayAdapter fitnessgoalAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, goalNameList);
        spinnerfitnessgoal.setAdapter(fitnessgoalAdapter);
        spinnerfitnessgoal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                selectedFit_goal_id = getSelectGoalId(goalNameList.get(pos));
                setInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setInfo(){
        for (int i = 0; i < fitnessGoalList.size(); i++) {
            if (selectedFit_goal_id == fitnessGoalList.get(i).getFit_goal_id()){
                Picasso.with(context).load("http://www.pttracker.co.uk/"+fitnessGoalList.get(i).getPic()).fit().into(planImage);
                days.setText(fitnessGoalList.get(i).getDays() + " DAYS");
                if (fitnessGoalList.get(i).getIntensity() == 1)
                workout.setText("EASY");
                else if (fitnessGoalList.get(i).getIntensity() == 2)
                    workout.setText("MEDIUM");
                else if (fitnessGoalList.get(i).getIntensity() == 3)
                    workout.setText("HARD");

            }
        }
    }


    int getSelectGoalId(String goal) {
        int id;
        for (int i = 0; i < fitnessGoalList.size(); i++) {
            if (goal.equalsIgnoreCase(fitnessGoalList.get(i).getGoal_description())) {
                return fitnessGoalList.get(i).getPlan_id();
            }
        }
        return 0;
    }
}
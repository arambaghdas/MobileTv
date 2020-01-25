package train.apitrainclient.views.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;
import com.pttrackershared.models.eventbus.TrainingPlan;
import com.squareup.picasso.Picasso;

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
import train.apitrainclient.listeners.OnLoginViaEmailCompletionListener;
import com.pttrackershared.models.eventbus.FitnessGoalModel;
import com.pttrackershared.models.eventbus.User;
import train.apitrainclient.networks.retrofit.RestApiManager;
import train.apitrainclient.services.DialogUtils;
import train.apitrainclient.utils.Constants;

/**
 * RoutinesFragment shows list of routines assigned to current user.
 * Provides option to user to filter out workout based on routine and date.
 */

public class Profile_FitnessGoal_Fragment extends BaseFragment {

    @BindView(R.id.goalDate)
    Spinner etGoalDate;

    @BindView(R.id.et_goal_weight)
    EditText etGoalWeight;

    @BindView(R.id.til_goal_weight)
    TextInputLayout til_goal_weight;

    @BindView(R.id.planImage)
    ImageView planImage;

    @BindView(R.id.days)
    TextView days;

    @BindView(R.id.workout)
    TextView workout;

    public static int calendarSelectedDate = 0;
    private DatePickerDialog datePickerDialog;
    static User user;
    int yesnoBtnSelectdValue = 0;
    Spinner spinnerfitnessgoal;
    Spinner spinnerPlans;

    int selectedFit_goal_id;
    int selected_plan_id;
    private List<FitnessGoalModel> fitnessGoalList;
    private List<TrainingPlan> trainingPlanList;
    public static String selectedFitnessGoal = "";


    public static Profile_FitnessGoal_Fragment newInstance(User userr) {
        user = userr;
        return new Profile_FitnessGoal_Fragment();
    }

    @Override
    public void preStart(LayoutInflater inflater, @Nullable ViewGroup container) {
        super.preStart(inflater, container);
        rootView = inflater.inflate(R.layout.fragment_profile_fitnessgoal, container, false);
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
//        calendarSelectedDate = Calendar.getInstance();
//        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//
//                Calendar calendar = Calendar.getInstance();
//                calendar.set(Calendar.YEAR, year);
//                calendar.set(Calendar.MONTH, monthOfYear);
//                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                calendarSelectedDate = calendar;
//                setTargetDate();
//            }
//        };

//        datePickerDialog = new DatePickerDialog(context, dateSetListener, calendarSelectedDate
//                .get(Calendar.YEAR), calendarSelectedDate.get(Calendar.MONTH),
//                calendarSelectedDate.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void postStart() {
        super.postStart();
        loadFitnessGoal();
        setWeeksGoal();
    }

    private void setTargetDate() {
//        Date dob = calendarSelectedDate.getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
//        String selectedDate = sdf.format(calendarSelectedDate.getTime());
//        etGoalDate.setText(selectedDate);
    }


//    @OnClick(R.id.et_goal_date)
//    public void targetDateClicked() {
//        hideKeyboard();
//        datePickerDialog.show();
//    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    @OnClick(R.id.btn_Next)
    void nextButtonClicked() {
        if (checkValidateion()) {
            user.setFitness_goal(selectedFit_goal_id);
            user.setWeightgoal(etGoalWeight.getText().toString());
            DialogUtils.ShowProgress(context, "Please wait...");
            RestApiManager.updateUserAccountLogin2(getActivity(), user.getTelephone(), user.getClientemail(), user, yesnoBtnSelectdValue, new OnLoginViaEmailCompletionListener() {
                @Override
                public void onSuccess(User user, String accessToken) {
                    yesnoBtnSelectdValue = 0;
                    DialogUtils.HideDialog();
                    moveToNextScreen();
                }

                @Override
                public void onFailure(int errorCode, String errorMessage) {
                    DialogUtils.HideDialog();
                    DialogUtils.ShowSnackbarAlert(context, errorMessage);
                }
            });
        }
    }

    @OnClick(R.id.btn_skip)
    void skipButtonClicked() {
        yesnoBtnSelectdValue = 1;
        moveToNextScreen();
    }

    void moveToNextScreen() {
        Fragment fragment = Profile_TrainingPlan_Fragment.newInstance(yesnoBtnSelectdValue, selectedFit_goal_id, selectedFitnessGoal);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.view_content, fragment);
        fragmentTransaction.addToBackStack("").commit();
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


    void setWeeksGoal() {
        List<String>weeks = new ArrayList<>();
        weeks.add("1 week");
        weeks.add("2 weeks");
        weeks.add("3 weeks");
        weeks.add("4 weeks");
        ArrayAdapter planAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, weeks);
        etGoalDate.setAdapter(planAdapter);
        etGoalDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                calendarSelectedDate = getSelectGoalId(weeks.get(pos)) + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                return fitnessGoalList.get(i).getFit_goal_id();
            }
        }
        return 0;
    }

    boolean checkValidateion() {
        boolean flag = true;

//        if (etGoalDate.getText().toString().length() <= 0) {
//            flag = false;
//            til_goal_date.setErrorEnabled(true);
//            til_goal_date.setError("Enter Goal Date.");
//        }

        if (etGoalWeight.getText().toString().length() <= 0) {
            flag = false;
            til_goal_weight.setErrorEnabled(true);
            til_goal_weight.setError("Enter Goal Weight.");
        }

        return flag;

    }
}
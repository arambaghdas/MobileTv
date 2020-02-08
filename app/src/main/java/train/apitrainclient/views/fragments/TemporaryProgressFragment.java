package train.apitrainclient.views.fragments;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.pttracker.trainingaid.fragments.LandingFragment;
import com.pttrackershared.models.eventbus.Circuit;
import com.pttrackershared.models.eventbus.Exercise;
import com.pttrackershared.models.eventbus.ProgressModel;
import com.pttrackershared.models.eventbus.TrainingPlan;
import com.pttrackershared.models.eventbus.TrainingPlanJsonModel;
import com.pttrackershared.models.eventbus.Workout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.StatsSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import train.apitrainclient.R;
import train.apitrainclient.listeners.OnFitnessGoalCompletionListener;
import train.apitrainclient.listeners.OnFoodListCompletionListener;
import train.apitrainclient.listeners.OnGetProgressDataCompletionListener;
import train.apitrainclient.listeners.OnGetTrainingPlansCompletionListener;

import com.pttrackershared.models.eventbus.FitnessGoalModel;
import com.pttrackershared.models.eventbus.Meals;
import com.pttrackershared.models.eventbus.MealsModel;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.User;

import train.apitrainclient.networks.retrofit.RestApiManager;
import train.apitrainclient.utils.AppUtils;
import train.apitrainclient.utils.Constants;
import train.apitrainclient.utils.NetworkUtils;
import train.apitrainclient.utils.OfflineDataProgressPreferences;
import train.apitrainclient.utils.SharedPrefManager;
import train.apitrainclient.utils.ValidatorUtils;
import train.apitrainclient.views.activities.HomeActivity;

/**
 * @author Atif Ali
 * @since August 29, 2017 5:08 PM
 * ProgressStatsFragment shows user info (full name and weight target) and weight graph.
 */

public class TemporaryProgressFragment extends BaseFragment {

    @BindView(R.id.homeLayout)
    RelativeLayout homeLayout;
    @BindView(R.id.loadingView)
    LinearLayout loadingView;
    @BindView(R.id.training_plan)
    TextView training_plan;
    @BindView(R.id.food_plan)
    TextView food_plan;
    @BindView(R.id.fitness_goal)
    TextView fit_goal;
    @BindView(R.id.pictureIv)
    ImageView plans_image;
    @BindView(R.id.tv_description)
    TextView tv_description;
    @BindView(R.id.userImage)
    ImageView userImage;
    @BindView(R.id.btn_monday)
    TextView btnMonday;
    @BindView(R.id.line_monday)
    View lineMonday;
    @BindView(R.id.btn_tuesday)
    TextView btnTuesday;
    @BindView(R.id.line_tuesday)
    View lineTuesday;
    @BindView(R.id.btn_wednesday)
    TextView btnWednesday;
    @BindView(R.id.line_wednesday)
    View lineWednesday;
    @BindView(R.id.btn_thuesday)
    TextView btnThuesday;
    @BindView(R.id.line_thuesday)
    View lineThuesday;
    @BindView(R.id.btn_friday)
    TextView btnFriday;
    @BindView(R.id.line_friday)
    View lineFriday;
    @BindView(R.id.btn_saturday)
    TextView btnSaturday;
    @BindView(R.id.line_saturday)
    View lineSaturday;
    @BindView(R.id.btn_sunday)
    TextView btnSunday;
    @BindView(R.id.line_sunday)
    View lineSunday;
    @BindView(R.id.workoutName)
    TextView workoutName;
    @BindView(R.id.caloriesWorkoutTv)
    TextView caloriesWorkoutTv;
    @BindView(R.id.haveWorkoutLayout)
    RelativeLayout haveWorkoutLayout;
    @BindView(R.id.noWorkoutData)
    RelativeLayout noWorkoutData;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.daysLayout)
    LinearLayout daysLayout;
    @BindView(R.id.plan_imageView)
    ImageView plan_imageView;
    @BindView(R.id.stepsWorkoutTv)
    TextView stepsWorkoutTv;
    @BindView(R.id.tv_more)
    TextView tv_more;
    @BindView(R.id.title1)
    TextView title1;
    @BindView(R.id.dp_steps)
    DonutProgress donutProgressSteps;
    @BindView(R.id.tv_steps_progress)
    TextView tvStepsProgress;
    @BindView(R.id.dp_calories)
    DonutProgress donutProgressCalories;
    @BindView(R.id.tv_calories_progress)
    TextView tvCaloriesProgress;
    @BindView(R.id.dp_fitness)
    DonutProgress donutProgressFitness;
    @BindView(R.id.tv_fitness_progress)
    TextView tvFitnessProgress;

    public static boolean loadingViewShown = false;
    //region Overridden Methods from BaseFragment
    private User user;
    int selectedDay = 0;
    private Calendar calendar;
    ArrayList<TrainingLog> trainingLogs = new ArrayList<>();
    ProgressModel model;

    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu);

        MenuItem item = menu.findItem(R.id.action_add);
        item.setVisible(false);

        MenuItem add_client = menu.findItem(R.id.action_add_client);
        add_client.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void preStart(LayoutInflater inflater, @Nullable ViewGroup container) {
        super.preStart(inflater, container);

        rootView = inflater.inflate(R.layout.fragment_temporary_progress, container, false);
        ButterKnife.bind(this, rootView);

        user = SharedPrefManager.getUser(context);
        com.pttrackershared.utils.Constants.user = user;
        calendar = Calendar.getInstance();

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        int count = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (count == 0) {
            selectedDay = 7;
        } else {
            selectedDay = count;
        }

        if (!loadingViewShown) {
            loadingView.setVisibility(View.VISIBLE);
            loadingViewShown = true;
        }
//        AppUtils.checkForGenderView(getActivity(),homeLayout);
        setHasOptionsMenu(true);

        getFitnessGoal();


        if (NetworkUtils.IsNetworkAvailable(getActivity()))
        getProgressData();
        else
        ProgressDataFill(model);

        initProgress();

    }

    private void initProgress() {
        donutProgressSteps.setTextColor(getResources().getColor(R.color.white));
        donutProgressSteps.setFinishedStrokeColor(getResources().getColor(R.color.red));
        donutProgressSteps.setUnfinishedStrokeColor(getResources().getColor(R.color.light_grey));

        donutProgressCalories.setTextColor(getResources().getColor(R.color.white));
        donutProgressCalories.setFinishedStrokeColor(getResources().getColor(R.color.red));
        donutProgressCalories.setUnfinishedStrokeColor(getResources().getColor(R.color.light_grey));

        donutProgressFitness.setTextColor(getResources().getColor(R.color.white));
        donutProgressFitness.setFinishedStrokeColor(getResources().getColor(R.color.red));
        donutProgressFitness.setUnfinishedStrokeColor(getResources().getColor(R.color.light_grey));
    }

    private void updateProgressValues(Day day) {
        int stepProgress = getStepsProgress(day);
        tvStepsProgress.setText(String.valueOf(stepProgress));
        donutProgressSteps.setProgress(stepProgress);

        if (stepProgress >= 100) {
            donutProgressSteps.setProgress(100);
        } else {
            donutProgressSteps.setProgress(stepProgress);
        }

        int workoutCaloriesProgress = getWorkoutCaloriesProgress(day);
        tvCaloriesProgress.setText(String.valueOf(workoutCaloriesProgress));
        donutProgressCalories.setProgress(workoutCaloriesProgress);

        if (workoutCaloriesProgress >= 100) {
            donutProgressCalories.setProgress(100);
        } else {
            donutProgressCalories.setProgress(workoutCaloriesProgress);
        }

        int workoutProgress = getWorkoutProgress(day);
        tvFitnessProgress.setText(String.valueOf(workoutProgress));

        if (workoutProgress >= 100) {
            donutProgressFitness.setProgress(100);
        } else {
            donutProgressFitness.setProgress(workoutProgress);
        }

    }

    private int getStepsProgress(Day day) {
        float stepsGoal = 0.0f;
        int steps = 0;

        if (model != null) {
            switch (day) {
                case MONDAY:
                    steps = model.getMonSteps();
                    break;
                case TUESDAY:
                    steps = model.getTueSteps();
                    break;
                case WEDNESDAY:
                    steps = model.getWedSteps();
                    break;
                case THURSDAY:
                    steps = model.getThuSteps();
                    break;
                case FRIDAY:
                    steps = model.getFriSteps();
                    break;
                case SATURDAY:
                    steps = model.getSatSteps();
                    break;
                case SUNDAY:
                    steps = model.getSunSteps();
                    break;
                default:
                    break;
            }
        }

        User user = SharedPrefManager.getUser(getContext());
        if (user != null && !ValidatorUtils.IsNullOrEmpty(user.getWeekGoalSteps())) {
            stepsGoal = Float.valueOf(user.getWeekGoalSteps());
        } else {
            return 0;
        }

        float progress = (steps/ stepsGoal) * 100.00f;
        return (int) progress;
    }

    private int getWorkoutCaloriesProgress(Day day) {
        float workoutCaloriesGoal = 0.0f;
        int workoutCalories = 0;

        if (model != null) {
            switch (day) {
                case MONDAY:
                    workoutCalories = model.getMonWorkoutCalories();
                    break;
                case TUESDAY:
                    workoutCalories = model.getTueWorkoutCalories();
                    break;
                case WEDNESDAY:
                    workoutCalories = model.getWedWorkoutCalories();
                    break;
                case THURSDAY:
                    workoutCalories = model.getThuWorkoutCalories();
                    break;
                case FRIDAY:
                    workoutCalories = model.getFriWorkoutCalories();
                    break;
                case SATURDAY:
                    workoutCalories = model.getSatWorkoutCalories();
                    break;
                case SUNDAY:
                    workoutCalories = model.getSunWorkoutCalories();
                    break;
                default:
                    break;
            }
        }

        User user = SharedPrefManager.getUser(getContext());
        if (user != null && !ValidatorUtils.IsNullOrEmpty(user.getWeekGoalKCal())) {
            workoutCaloriesGoal = Float.valueOf(user.getWeekGoalKCal());
        } else {
            return 0;
        }

        float progress = (workoutCalories/ workoutCaloriesGoal) * 100.00f;
        return (int) progress;
    }

    private int getWorkoutProgress(Day day) {
        float workoutGoal = 0.0f;
        int workout = 0;

        if (model != null) {
            switch (day) {
                case MONDAY:
                    workout = model.getMonWorkout();
                    break;
                case TUESDAY:
                    workout = model.getTueWorkout();
                    break;
                case WEDNESDAY:
                    workout = model.getWedWorkout();
                    break;
                case THURSDAY:
                    workout = model.getThuWorkout();
                    break;
                case FRIDAY:
                    workout = model.getFriWorkout();
                    break;
                case SATURDAY:
                    workout = model.getSatWorkout();
                    break;
                case SUNDAY:
                    workout = model.getSunWorkout();
                    break;
                default:
                    break;
            }
        }

        User user = SharedPrefManager.getUser(getContext());
        if (user != null && !ValidatorUtils.IsNullOrEmpty(user.getWeekGoalWorkout())) {
            workoutGoal = Float.valueOf(user.getWeekGoalWorkout());
        } else {
            return 0;
        }

        float progress = (workout/ workoutGoal) * 100.00f;
        return (int) progress;
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

        loadGraphInfo();

        String planName = SharedPrefManager.getTrainingPlanName(getActivity());
        if (planName != null && !planName.isEmpty()) {
            training_plan.setText(planName);
        }

        String foodPlanName = SharedPrefManager.getFoodPlanName(getActivity());
        if (foodPlanName != null && !foodPlanName.isEmpty()) {
            food_plan.setText(foodPlanName);
        }

        setFoodPlan();


    }

    @OnClick(R.id.btn_start_workout_progress)
    public void LinkShop() {
        HomeActivity.getInstance().currentFragmentIndex = 9;
        if (HomeActivity.getInstance().expiredTrial) {
            HomeActivity.getInstance().fragment = UpgradeFragment.newInstance();
        } else {
            HomeActivity.getInstance().fragment = LandingFragment.newInstance();
        }

        HomeActivity.getInstance().tvTitle.setText("WORKOUT");
        HomeActivity.getInstance().addContentFragment(HomeActivity.getInstance().fragment);

    }

    public void getProgressData() {
        RestApiManager.getProgressInfoData(getActivity(), new OnGetProgressDataCompletionListener() {
            @Override
            public void onSuccess(ProgressModel progressModel) {
                model = progressModel;
                OfflineDataProgressPreferences.addProgress(progressModel,getActivity());
                ProgressDataFill(model);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {

            }
        });
    }


    public void ProgressDataFill(ProgressModel progressModel){
        model = progressModel;
        if (model == null){
            model = OfflineDataProgressPreferences.getProgress(getActivity());
        }

        training_plan.setText(model.getTraining_plan());
        SharedPrefManager.setTraininPlanName(model.getTraining_plan(), context);
        SharedPrefManager.setFoodPlanName(model.getFood_plan(), context);
        if (model.getPicture().contains(".jpeg")) {
            String imageName = model.getPicture().substring(0, model.getPicture().length() - 5);

            String image = imageName + ".jpg";
            Picasso.with(context).load("http://www.pttracker.co.uk/" + image).fit().into(plans_image);

        } else
            Picasso.with(context).load("http://www.pttracker.co.uk/" + model.getPicture()).fit().into(plans_image);

        tv_description.setText(model.getDescription());

        title1.setText(model.getTitle());

        if (model.getDescription().length() > 190){
            tv_more.setVisibility(View.VISIBLE);
            tv_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tv_more.getText().toString().equalsIgnoreCase("more..")){
                        tv_description.setMaxLines(100);
                        tv_more.setText("less..");
                    }else {
                        tv_description.setMaxLines(6);
                        tv_more.setText("more..");
                    }


                }
            });
        }

        if(model.getTraining_plan().equalsIgnoreCase("0")){
            model.setTraining_plan("No Training Plan");
            training_plan.setText("No Training Plan");
        }else
        training_plan.setText(model.getTraining_plan());

        if(model.getFood_plan().equalsIgnoreCase("0")){
            model.setFood_plan("No Food Plan");
        }
        food_plan.setText(model.getFood_plan());

        if (selectedDay == 1) {
            btnMonday.performClick();
        } else if (selectedDay == 2) {
            btnTuesday.performClick();
        } else if (selectedDay == 3) {
            btnWednesday.performClick();
        } else if (selectedDay == 4) {
            btnThuesday.performClick();
        } else if (selectedDay == 5) {
            btnFriday.performClick();
        } else if (selectedDay == 6) {
            btnSaturday.performClick();
        } else if (selectedDay == 7) {
            btnSunday.performClick();
        }
    }

    //    @OnClick(R.id.fitness_goal)
//    public void fintessUpdate(){
//        HomeActivity.getInstance().currentFragmentIndex = 2;
//        HomeActivity.getInstance().showDefaultFragment();
//
//    }
    private void setUserInfo() {
        User user = SharedPrefManager.getUser(context);
//        if (!ValidatorUtils.IsNullOrEmpty(user.getImage())) {
//            String imageP = user.getImage();
//
//            String imageName = "";
//            if (imageP.contains("/")) {
//                imageName = imageP.substring(imageP.lastIndexOf("/"), imageP.length());
//            } else {
//                imageName = user.getImage();
//            }
//
//            if (imageName.contains("{")) {
//                String imageSplit[] = imageName.split("=");
//                imageName = imageSplit[1].substring(0, imageSplit[1].length() - 1);
//
//            }
//
//            if (!imageName.contains(".jpg")) {
//                if (imageName.contains(".jpeg")) {
//                    String imageSplit[] = imageName.split(".jpeg");
//                    imageName = imageSplit[0] + ".jpg";
//                } else
//                    imageName = imageName + ".jpg";
//            }
//
//            Picasso.with(context).load(Constants.IMAGE_URL +
//                    imageName).
//                    error(R.drawable.avatar).into(userImage);
//
//        } else {
//            if (user.getGender().equalsIgnoreCase("2")) {
//                userImage.setImageResource(R.drawable.avatar);
//            } else {
//                userImage.setImageResource(R.drawable.avatar);
//            }
//
//        }
    }

    @OnClick(R.id.training_plan)
    public void checkPlanView() {
        HomeActivity.getInstance().currentFragmentIndex = 2;
        HomeActivity.getInstance().showDefaultFragment();

    }

    @OnClick(R.id.food_plan)
    public void checkFoodView() {
        HomeActivity.getInstance().currentFragmentIndex = 3;
        HomeActivity.getInstance().showDefaultFragment();
    }
//
//    @OnClick(R.id.watchLink1)
//    public void Link1(){
//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.apitrain.com/index.php?page=smart-watch-apitrain")));
//
//    }
//
//    @OnClick(R.id.watchLink2)
//    public void Link2(){
//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.apitrain.com/index.php?page=smart-watch-apitrain")));
//
//    }
//
//    @OnClick(R.id.watchLink3)
//    public void Link3(){
//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.apitrain.com/index.php?page=smart-watch-apitrain")));
//
//    }

    //region Business Logic Specific to this fragment

    public static TemporaryProgressFragment newInstance() {
        TemporaryProgressFragment progressStatsFragment = new TemporaryProgressFragment();
        return progressStatsFragment;
    }

    private void loadGraphInfo() {
        User user = SharedPrefManager.getUser(context);
        RestApiManager.getTrainingPlans(context, user, new OnGetTrainingPlansCompletionListener() {
            @Override
            public void onSuccess(List<TrainingPlanJsonModel> trainingPlanListView) {
                List<TrainingPlan> trainingPlanList = AppUtils.saveTrainingPlans(getActivity(), trainingPlanListView);
                int size = trainingPlanList.size();
                if (size == 0) {
                    HomeActivity.trainingPlan = false;
                    SharedPrefManager.setTraininPlanName("No training plan", getActivity());
                    training_plan.setText("No training plan");
                } else {
                    for (int i = 0; i < trainingPlanList.size(); i++) {
                        TrainingPlan trainingPlan = trainingPlanList.get(i);
                        if (trainingPlan.getName() != null && !trainingPlan.getName().isEmpty()) {
                            HomeActivity.trainingPlan = true;
                            SharedPrefManager.setTrainingPlan(context, trainingPlan);
                        }
                    }

                    StatsSnapshot picassoStats = Picasso.with(getActivity()).getSnapshot();
                    int images = picassoStats.originalBitmapCount;
                    if (images > 0) {
                    } else {
                        if (trainingPlanList != null && trainingPlanList.size() > 0) {
                            for (int j = 0; j < trainingPlanList.size(); j++) {
                                TrainingPlan trainingPlan = trainingPlanList.get(j);
                                for (int k = 0; k < trainingPlan.getWorkoutList().size(); k++) {
                                    Workout workout = trainingPlan.getWorkoutList().get(k);
                                    for (int a = 0; a < workout.getCircuitList().size(); a++) {
                                        Circuit circuit = workout.getCircuitList().get(a);
                                        for (int b = 0; b < circuit.getExerciseList().size(); b++) {
                                            Exercise exercise = TrainingLog.convertToExercise(circuit.getExerciseList().get(b));
                                            if (user.getGender().equalsIgnoreCase("2"))
                                                Picasso.with(context).load(Constants.IMAGE_URL_FEMALE + exercise.getImageLink()).fetch();
                                            else
                                                Picasso.with(context).load(Constants.IMAGE_URL_MALE + exercise.getImageLink()).fetch();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                loadingView.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
//                Toast.makeText(getActivity(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                loadingView.setVisibility(View.GONE);
            }
        });
    }

    public void setFoodPlan() {
        Date date = new Date();
        date.getTime();

        RestApiManager.getFoodList(context, new OnFoodListCompletionListener() {
            @Override
            public void onSuccess(List<Meals> userJsonModelList) {
                if (userJsonModelList.size() > 0) {
                    MealsModel mealsModel = new MealsModel();
                    mealsModel.model = (ArrayList<Meals>) userJsonModelList;
                    SharedPrefManager.setMeals(context, mealsModel);
                    date.getTime();
                } else {
                    MealsModel mealsModel = new MealsModel();
                    mealsModel.model = new ArrayList<>();
                    SharedPrefManager.setMeals(context, mealsModel);
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {

            }
        });
    }

    public void getFitnessGoal() {

        Picasso.with(context).load("http://www.pttracker.co.uk/uploads/fitnessgoal/default.jpg").fit().into(plan_imageView);

        RestApiManager.getFitnessGoal(context, new OnFitnessGoalCompletionListener() {
            @Override
            public void onSuccess(List<FitnessGoalModel> fitnessGoalModelList) {
                for (int i = 0; i < fitnessGoalModelList.size(); i++) {
                    if (fitnessGoalModelList.get(i).getFit_goal_id() == user.getFitness_goal()) {
                        fit_goal.setText(fitnessGoalModelList.get(i).getGoal_description());
                        if (fitnessGoalModelList.get(i).getPic().contains(".jpeg")) {
                            String imageName = fitnessGoalModelList.get(i).getPic().substring(0, fitnessGoalModelList.get(i).getPic().length() - 5);

                            String image = imageName + ".jpg";
                            Picasso.with(context).load("http://www.pttracker.co.uk" + image).fit().into(plan_imageView);

                        } else
                            Picasso.with(context).load("http://www.pttracker.co.uk" + fitnessGoalModelList.get(i).getPic()).fit().into(plan_imageView);
                    }
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {

            }
        });
    }


    public void setImageData(int workout) {
        switch (workout) {
            case 1:
                Picasso.with(context).load(R.mipmap.easy_colour).fit().into(img1);
                img2.setVisibility(View.GONE);
                img3.setVisibility(View.GONE);
                break;
            case 2:
                Picasso.with(context).load(R.mipmap.medium_colour).fit().into(img2);
                img1.setVisibility(View.GONE);
                img3.setVisibility(View.GONE);
                break;
            case 3:
                Picasso.with(context).load(R.mipmap.hard_colour).fit().into(img3);
                img1.setVisibility(View.GONE);
                img2.setVisibility(View.GONE);
                break;
        }
    }

    public void populateWorkoutArea(int day, ProgressModel model) {
        noWorkoutData.setVisibility(View.GONE);
        haveWorkoutLayout.setVisibility(View.VISIBLE);
        switch (day) {
            case 1:
                workoutName.setText(model.getMonWorkoutName());
                stepsWorkoutTv.setText(model.getMonSteps() + "");
                caloriesWorkoutTv.setText(model.getMonWorkoutCalories() + "");
                if (model.getMonWorkout() == 0) {
                    noWorkoutData.setVisibility(View.VISIBLE);
                    haveWorkoutLayout.setVisibility(View.GONE);
                } else {
                    setImageData(model.getMonWorkout());
                }
                break;
            case 2:
                workoutName.setText(model.getTueWorkoutName());
                stepsWorkoutTv.setText(model.getTueSteps() + "");
                caloriesWorkoutTv.setText(model.getMonWorkoutCalories() + "");
                if (model.getTueWorkout() == 0) {
                    noWorkoutData.setVisibility(View.VISIBLE);
                    haveWorkoutLayout.setVisibility(View.GONE);
                } else {
                    setImageData(model.getTueWorkout());
                }
                break;
            case 3:
                workoutName.setText(model.getWedWorkoutName());
                stepsWorkoutTv.setText(model.getWedSteps() + "");
                caloriesWorkoutTv.setText(model.getMonWorkoutCalories() + "");
                if (model.getWedWorkout() == 0) {
                    noWorkoutData.setVisibility(View.VISIBLE);
                    haveWorkoutLayout.setVisibility(View.GONE);
                } else {
                    setImageData(model.getWedWorkout());
                }
                break;
            case 4:
                workoutName.setText(model.getThuWorkoutName());
                stepsWorkoutTv.setText(model.getThuSteps() + "");
                caloriesWorkoutTv.setText(model.getMonWorkoutCalories() + "");
                if (model.getThuWorkout() == 0) {
                    noWorkoutData.setVisibility(View.VISIBLE);
                    haveWorkoutLayout.setVisibility(View.GONE);
                } else {
                    setImageData(model.getThuWorkout());
                }
                break;
            case 5:
                workoutName.setText(model.getFriWorkoutName());
                stepsWorkoutTv.setText(model.getFriSteps() + "");
                caloriesWorkoutTv.setText(model.getMonWorkoutCalories() + "");
                if (model.getFriWorkout() == 0) {
                    noWorkoutData.setVisibility(View.VISIBLE);
                    haveWorkoutLayout.setVisibility(View.GONE);
                } else {
                    setImageData(model.getFriWorkout());
                }
                break;
            case 6:
                workoutName.setText(model.getSatWorkoutName());
                stepsWorkoutTv.setText(model.getSatSteps() + "");
                caloriesWorkoutTv.setText(model.getMonWorkoutCalories() + "");
                if (model.getSatWorkout() == 0) {
                    noWorkoutData.setVisibility(View.VISIBLE);
                    haveWorkoutLayout.setVisibility(View.GONE);
                } else {
                    setImageData(model.getSatWorkout());
                }
                break;
            case 7:
                workoutName.setText(model.getSunWorkoutName());
                stepsWorkoutTv.setText(model.getSunSteps() + "");
                caloriesWorkoutTv.setText(model.getMonWorkoutCalories() + "");
                if (model.getSunWorkout() == 0) {
                    noWorkoutData.setVisibility(View.VISIBLE);
                    haveWorkoutLayout.setVisibility(View.GONE);
                } else {
                    setImageData(model.getSunWorkout());
                }
                break;
        }
    }

    @OnClick(R.id.btn_monday)
    void clickedBtnMonday() {
        selectedDay = 1;
        populateWorkoutArea(selectedDay, model);
        for (int i = 0; i < daysLayout.getChildCount(); i++) {
            View layout = daysLayout.getChildAt(i);
            if (layout instanceof LinearLayout) {
                for (int j = 0; j < ((LinearLayout) layout).getChildCount(); j++) {
                    View line = ((LinearLayout) layout).getChildAt(j);
                    if (line instanceof TextView) {
                        ((TextView) line).setTextColor(context.getResources().getColor(R.color.light_grey));
                    } else {
                        line.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                }

            }
        }

        lineMonday.setBackgroundColor(context.getResources().getColor(R.color.green_grapgh));
        btnMonday.setTextColor(context.getResources().getColor(R.color.background_blue));
        updateProgressValues(Day.MONDAY);
    }

    @OnClick(R.id.btn_tuesday)
    void clickedBtnTuesday() {
        selectedDay = 2;
        populateWorkoutArea(selectedDay, model);
        for (int i = 0; i < daysLayout.getChildCount(); i++) {
            View layout = daysLayout.getChildAt(i);
            if (layout instanceof LinearLayout) {
                for (int j = 0; j < ((LinearLayout) layout).getChildCount(); j++) {
                    View line = ((LinearLayout) layout).getChildAt(j);
                    if (line instanceof TextView) {
                        ((TextView) line).setTextColor(context.getResources().getColor(R.color.light_grey));
                    } else {
                        line.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                }

            }
        }
        lineTuesday.setBackgroundColor(context.getResources().getColor(R.color.green_grapgh));
        btnTuesday.setTextColor(context.getResources().getColor(R.color.background_blue));
        updateProgressValues(Day.TUESDAY);
    }

    @OnClick(R.id.btn_wednesday)
    void clickedBtnWednesday() {
        selectedDay = 3;
        populateWorkoutArea(selectedDay, model);
        for (int i = 0; i < daysLayout.getChildCount(); i++) {
            View layout = daysLayout.getChildAt(i);
            if (layout instanceof LinearLayout) {
                for (int j = 0; j < ((LinearLayout) layout).getChildCount(); j++) {
                    View line = ((LinearLayout) layout).getChildAt(j);
                    if (line instanceof TextView) {
                        ((TextView) line).setTextColor(context.getResources().getColor(R.color.light_grey));
                    } else {
                        line.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                }

            }
        }
        lineWednesday.setBackgroundColor(context.getResources().getColor(R.color.green_grapgh));
        btnWednesday.setTextColor(context.getResources().getColor(R.color.background_blue));
        updateProgressValues(Day.WEDNESDAY);
    }

    @OnClick(R.id.btn_thuesday)
    void clickedBtnThursday() {
        selectedDay = 4;
        populateWorkoutArea(selectedDay, model);
        for (int i = 0; i < daysLayout.getChildCount(); i++) {
            View layout = daysLayout.getChildAt(i);
            if (layout instanceof LinearLayout) {
                for (int j = 0; j < ((LinearLayout) layout).getChildCount(); j++) {
                    View line = ((LinearLayout) layout).getChildAt(j);
                    if (line instanceof TextView) {
                        ((TextView) line).setTextColor(context.getResources().getColor(R.color.light_grey));
                    } else {
                        line.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                }

            }
        }
        lineThuesday.setBackgroundColor(context.getResources().getColor(R.color.green_grapgh));
        btnThuesday.setTextColor(context.getResources().getColor(R.color.background_blue));
        updateProgressValues(Day.THURSDAY);
    }

    @OnClick(R.id.btn_friday)
    void clickedBtnFriday() {
        selectedDay = 5;
        populateWorkoutArea(selectedDay, model);
        for (int i = 0; i < daysLayout.getChildCount(); i++) {
            View layout = daysLayout.getChildAt(i);
            if (layout instanceof LinearLayout) {
                for (int j = 0; j < ((LinearLayout) layout).getChildCount(); j++) {
                    View line = ((LinearLayout) layout).getChildAt(j);
                    if (line instanceof TextView) {
                        ((TextView) line).setTextColor(context.getResources().getColor(R.color.light_grey));
                    } else {
                        line.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                }

            }
        }
        lineFriday.setBackgroundColor(context.getResources().getColor(R.color.green_grapgh));
        btnFriday.setTextColor(context.getResources().getColor(R.color.background_blue));
        updateProgressValues(Day.FRIDAY);
    }

    @OnClick(R.id.btn_saturday)
    void clickedBtnSaturday() {
        selectedDay = 6;
        populateWorkoutArea(selectedDay, model);
        for (int i = 0; i < daysLayout.getChildCount(); i++) {
            View layout = daysLayout.getChildAt(i);
            if (layout instanceof LinearLayout) {
                for (int j = 0; j < ((LinearLayout) layout).getChildCount(); j++) {
                    View line = ((LinearLayout) layout).getChildAt(j);
                    if (line instanceof TextView) {
                        ((TextView) line).setTextColor(context.getResources().getColor(R.color.light_grey));
                    } else {
                        line.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                }

            }
        }
        lineSaturday.setBackgroundColor(context.getResources().getColor(R.color.green_grapgh));
        btnSaturday.setTextColor(context.getResources().getColor(R.color.background_blue));
        updateProgressValues(Day.SATURDAY);
    }

    @OnClick(R.id.btn_sunday)
    void clickedBtnSunday() {
        selectedDay = 7;
        populateWorkoutArea(selectedDay, model);
        for (int i = 0; i < daysLayout.getChildCount(); i++) {
            View layout = daysLayout.getChildAt(i);
            if (layout instanceof LinearLayout) {
                for (int j = 0; j < ((LinearLayout) layout).getChildCount(); j++) {
                    View line = ((LinearLayout) layout).getChildAt(j);
                    if (line instanceof TextView) {
                        ((TextView) line).setTextColor(context.getResources().getColor(R.color.light_grey));
                    } else {
                        line.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                }

            }
        }
        lineSunday.setBackgroundColor(context.getResources().getColor(R.color.green_grapgh));
        btnSunday.setTextColor(context.getResources().getColor(R.color.background_blue));
        updateProgressValues(Day.SUNDAY);
    }

}
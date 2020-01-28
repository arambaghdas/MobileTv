package train.apitrainclient.views.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.view.menu.MenuBuilder;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.pttracker.trainingaid.fragments.LandingFragment;
import com.pttracker.trainingaid.utils.ApiCallsHandler;
import com.pttracker.trainingaid.utils.ListenersHandler;
import com.pttracker.trainingaid.utils.SaveUserPreferences;
import com.pttrackershared.models.eventbus.Circuit;
import com.pttrackershared.models.eventbus.Exercise;
import com.pttrackershared.models.eventbus.TrainingPlan;
import com.pttrackershared.models.eventbus.TrainingPlanJsonModel;
import com.pttrackershared.models.eventbus.Workout;
import com.pttrackershared.models.eventbus.WorkoutJsonModel;
import com.pttrackershared.utils.TrainingPlanPreferences;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import train.apitrainclient.R;
import train.apitrainclient.listeners.OnFoodListCompletionListener;
import train.apitrainclient.listeners.OnGetCircuitCompletionListener;
import train.apitrainclient.listeners.OnGetExerciseCompletionListener;
import train.apitrainclient.listeners.OnGetGraphInfoCompletionListener;
import train.apitrainclient.listeners.OnGetTrainingLogsCompletionListener;
import train.apitrainclient.listeners.OnGetTrainingPlanListener;
import train.apitrainclient.listeners.OnGetWorkoutsCompletionListener;
import train.apitrainclient.listeners.OnUpdateUserAccountCompletionListener;
import com.pttrackershared.models.eventbus.CircuitsModel;
import com.pttrackershared.models.eventbus.ExercisesModel;
import com.pttrackershared.models.eventbus.GraphInfo;
import com.pttrackershared.models.eventbus.GraphInfoItem;
import com.pttrackershared.models.eventbus.Meals;
import com.pttrackershared.models.eventbus.MealsModel;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.TrainingLogsModel;
import com.pttrackershared.models.eventbus.TrainingPlanView;
import com.pttrackershared.models.eventbus.User;
import com.pttrackershared.models.eventbus.WorkoutsModel;
import train.apitrainclient.networks.retrofit.RestApiManager;
import train.apitrainclient.receivers.NetworkStateReceiver;
import train.apitrainclient.services.DialogUtils;
import train.apitrainclient.utils.AppUtils;
import train.apitrainclient.utils.Constants;
import train.apitrainclient.utils.GraphHelpers;
import train.apitrainclient.utils.NetworkUtils;
import train.apitrainclient.utils.SharedPrefManager;
import train.apitrainclient.utils.TimeUtils;
import train.apitrainclient.utils.UserPrefManager;
import train.apitrainclient.utils.ValidatorUtils;
import train.apitrainclient.views.fragments.FoodListFragments;
import train.apitrainclient.views.fragments.HelpFeedbackFragment;
import train.apitrainclient.views.fragments.NoTrainingPlanFragment;
import train.apitrainclient.views.fragments.ProfileFragment;
import train.apitrainclient.views.fragments.TemporaryProgressFragment;
import train.apitrainclient.views.fragments.TrainingLogsFragment;
import train.apitrainclient.views.fragments.TrainingPlanFragment;
import train.apitrainclient.views.fragments.Update_FitnessGoal_Fragment;
import train.apitrainclient.views.fragments.UpgradeFragment;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, DialogUtils.WeightUpdateCallback , NetworkStateReceiver.NetworkStateReceiverListener, OnUpdateUserAccountCompletionListener {

    @BindView(R.id.nav_view)
    NavigationView nvMenu;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.tv_title)
    public TextView tvTitle;

    @BindView(R.id.dividerHomeScreen)
    View dividerHomeScreen;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    ImageView ivProfileImage;
    TextView tvFullName;
    TextView tvGoal;
    TextView tvDaysTrained;
//    TextView workoutActive, workoutMax;
//    TextView trainingActive, trainingMax;
//    TextView foodActive, foodMax;
    public Fragment fragment;

    private Menu menu;
    private Fragment currentFragment;
    private boolean isBackPressedOnce;
    public static int currentFragmentIndex;
    private static final int SETTING_REQUEST_CODE = 1221;
    private int clientMaxInt, workoutMaxInt, trainingMaxInt, foodMaxInt;
    int trainingPlanActiveInt = 0;
    int clientsActiveInt = 0;
    int workoutActiveInt = 0;
    int foodActiveInt = 0;
    public User user;
    private Handler backPressedHandler = new Handler();
    private Runnable backRunnable = new Runnable() {
        @Override
        public void run() {
            isBackPressedOnce = false;
        }
    };
    public static HomeActivity homeActivity;
    public static TrainingLogsModel trainingLogsModel;
    public static WorkoutsModel workoutsModel;
    private CircuitsModel circuitsModel;
    public boolean comingFromResult = false;
    NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
    TrainingLog trainingLog;
    TrainingLogsModel trainingLogsModelSync;
    public boolean expiredTrial = false;
    public static boolean trainingPlan = true;
    public static GraphInfo graphInfo;
    public UserPrefManager userPrefManager;
    private boolean backFromWeeklyGoals = false;

    public static HomeActivity getInstance() {
        return homeActivity;
    }


    @Override
    protected void onResume() {
        super.onResume();
        homeActivity = this;
        setUserInfo();
        updateNavProgressFragment();
    }

    private void updateNavProgressFragment() {
        if (backFromWeeklyGoals) {
            backFromWeeklyGoals = false;
            MenuItem menuItem = nvMenu.getCheckedItem();
            if (menuItem != null && menuItem.getItemId() == R.id.nav_progress) {
                addNavProgressFragment();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void preStart() {
        super.preStart();
        setContentView(R.layout.activity_home);
        userPrefManager = new UserPrefManager(this);
        user = SharedPrefManager.getUser(HomeActivity.this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        trainingLogsModelSync = SharedPrefManager.getTrainingLogsSync(this);
        Menu menu = navigationView.getMenu();
    }

    @Override
    public void initViews() {
        super.initViews();
        ivProfileImage = (ImageView) nvMenu.getHeaderView(0).findViewById(R.id.iv_profile_image);
        tvFullName = (TextView) nvMenu.getHeaderView(0).findViewById(R.id.tv_full_name);
//        workoutActive = (TextView) nvMenu.getHeaderView(0).findViewById(R.id.workoutActive);
//        workoutMax = (TextView) nvMenu.getHeaderView(0).findViewById(R.id.workoutMax);
//        trainingActive = (TextView) nvMenu.getHeaderView(0).findViewById(R.id.trainingActive);
//        trainingMax = (TextView) nvMenu.getHeaderView(0).findViewById(R.id.trainingMax);
//        foodActive = (TextView) nvMenu.getHeaderView(0).findViewById(R.id.foodActive);
//        foodMax = (TextView) nvMenu.getHeaderView(0).findViewById(R.id.foodMax);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public void initListeners() {
        super.initListeners();
        nvMenu.setNavigationItemSelectedListener(this);
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfileScreen();
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void postStart() {
        super.postStart();

        if (checkForExpiredTrial() > 15){
            expiredTrial = true;
        }

        showDefaultFragment();

        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (!isBackPressedOnce) {
            isBackPressedOnce = true;
            backPressedHandler.postDelayed(backRunnable, 3000);
            Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_weekly_goals) {
            drawerLayout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this, WeeklyGoalsActivity.class));
            backFromWeeklyGoals = true;
            return false;
        } else {
            addFragment(item.getItemId());
            backFromWeeklyGoals = false;
        }
        return true;
    }

    private void addFragment(int menuId) {
        fragment = null;
        switch (menuId) {
            case R.id.nav_progress:
                addNavProgressFragment();
                break;
            case R.id.nav_training_logs:
                currentFragmentIndex = 1;
                backToNormalColor();
                if (expiredTrial){
                    fragment = UpgradeFragment.newInstance();
                }else {
                    fragment = TrainingLogsFragment.newInstance();
                }
                nvMenu.setCheckedItem(R.id.nav_training_logs);
                setTitle("TRAINING LOGS");
                setDividerVisible(true);
                addContentFragment(fragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

//            case R.id.nav_ftiness_plan:
//                currentFragmentIndex = 5;
//                if (expiredTrial){
//                    fragment = UpgradeFragment.newInstance();
//                }else {
//                    fragment = Update_FitnessGoal_Fragment.newInstance(SharedPrefManager.getUser(HomeActivity.this));
//                }
//                changeToolbarColor(R.color.white);
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu_blue);
//                setOverflowButtonColor(toolbar, getResources().getColor(R.color.background_blue));
//                nvMenu.setCheckedItem(R.id.nav_ftiness_plan);
//                setTitle("FITNESS GOAL");
//                break;

            case R.id.nav_training_plan:
                currentFragmentIndex = 2;
                backToNormalColor();

                if (!NetworkUtils.IsNetworkAvailable(HomeActivity.this)){
                    TrainingPlan trainingPlanModel = SharedPrefManager.getTrainingPlan(HomeActivity.this);
                    if (trainingPlanModel == null || trainingPlanModel.getWorkoutList() == null){
                        trainingPlan = false;
                    }else trainingPlan = true;
                }

                if (expiredTrial){
                    fragment = UpgradeFragment.newInstance();
                }else if (!trainingPlan){
                    fragment = NoTrainingPlanFragment.newInstance("training plan",false);
                }else {
                    fragment = TrainingPlanFragment.newInstance();
                }
                nvMenu.setCheckedItem(R.id.nav_training_plan);
                setTitle("VIEW TRAINING PLAN");
                setDividerVisible(true);
                addContentFragment(fragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_foodplan_list:

                if (!NetworkUtils.IsNetworkAvailable(HomeActivity.this)){
                    MealsModel mealsModel = SharedPrefManager.getMeals(HomeActivity.this);
                    if (mealsModel != null && mealsModel.model != null && mealsModel.model.size() > 0){
                        Constants.foodplan = true;
                    }else Constants.foodplan = false;
                }


                currentFragmentIndex = 3;
                setDividerVisible(true);
                backToNormalColor();
                if (expiredTrial){
                    fragment = UpgradeFragment.newInstance();
                }else if (!Constants.foodplan){
                    fragment = NoTrainingPlanFragment.newInstance("food plan",false);
                }else {
                    fragment = FoodListFragments.newInstance();
                }
                nvMenu.setCheckedItem(R.id.nav_foodplan_list);
                setTitle("FOOD PLAN");
                addContentFragment(fragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_help_feedback:
                currentFragmentIndex = 4;
                if (expiredTrial){
                    fragment = UpgradeFragment.newInstance();
                }else {
                    fragment = HelpFeedbackFragment.newInstance();
                }
                changeToolbarColor(R.color.white);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu_blue);
                setOverflowButtonColor(toolbar, getResources().getColor(R.color.background_blue));
                nvMenu.setCheckedItem(R.id.nav_foodplan_list);
                setTitle("HELP & FEEDBACK");
                addContentFragment(fragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

        }
    }

    private void addNavProgressFragment() {
        backToNormalColor();
        currentFragmentIndex = 0;
        GraphHelpers.fromHomeScreen = true;
        if (expiredTrial){
            fragment = UpgradeFragment.newInstance();
        }else {
            fragment = TemporaryProgressFragment.newInstance();
        }
        nvMenu.setCheckedItem(R.id.nav_progress);
        setTitle("MY PROGRESS");
        setDividerVisible(true);
        addContentFragment(fragment);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void backToNormalColor() {
        changeToolbarColor(R.color.transparent);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu_white);
        setOverflowButtonColor(toolbar, getResources().getColor(R.color.white));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            View view = findViewById(R.id.action_add);
            showPopupView(view);
        }  else if (id == R.id.action_about) {
            showAboutScreen();
        } else if (id == R.id.action_add_client) {
            return true;
        }

        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu instanceof MenuBuilder) {

            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }

        return true;
    }

    private void showPopupView(View v) {
        if (v != null) {
            PopupMenu popup = new PopupMenu(this, v);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.popup_menu_home, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.addWeight:
                            String message = "";
                            int maxVal = 0;
                            int minVal = 0;
                            if (User.getSelectedWeightUnit(context) == Constants.WEIGHT_UNIT_POUNDS) {
                                minVal = 40;
                                maxVal = 660;
                                message = "Slide to select weight in pounds.";
                            } else {
                                minVal = 20;
                                maxVal = 300;
                                message = "Slide to select weight in kilograms.";
                            }
                            DialogUtils.showInputDialog(context, minVal, maxVal, message, HomeActivity.this);
                            return true;
                        case R.id.showLog:
                            onNavigationItemSelected(nvMenu.getMenu().getItem(1));
                            return true;
                        default:
                            return false;
                    }
                }
            });
            popup.show();
        }
    }

    public void showDefaultFragment() {
        if (currentFragmentIndex == 9){
            fragment = LandingFragment.newInstance();
            setTitle("Training aid");
            addContentFragment(fragment);
        }else
        onNavigationItemSelected(nvMenu.getMenu().getItem(currentFragmentIndex));
    }

    public void addContentFragment(Fragment fragment) {
        currentFragment = fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.view_content, fragment).commit();
    }


    private void showProfileScreen() {
        drawerLayout.closeDrawer(GravityCompat.START);
        currentFragmentIndex = 0;
        backToNormalColor();
        fragment = ProfileFragment.newInstance();
        setTitle("Profile");
        addContentFragment(fragment);
    }


    private void showAboutScreen() {
        startActivity(new Intent(context, AboutActivity.class));
    }

    private void setUserInfo() {
        User user = SharedPrefManager.getUser(context);
        tvFullName.setText(user.getFirstname() + " " + user.getSurname());
        if (!ValidatorUtils.IsNullOrEmpty(user.getImage())) {
            String imageP = user.getImage();

            String imageName = "";
            if (imageP.contains("/")) {
                imageName = imageP.substring(imageP.lastIndexOf("/"), imageP.length());
            } else {
                imageName = user.getImage();
            }

            if (imageName.contains("{")){
                String imageSplit[] = imageName.split("=");
                imageName = imageSplit[1].substring(0,imageSplit[1].length() - 1);

            }

            if (!imageName.contains(".jpg")){
                if (imageName.contains(".jpeg")){
                    String imageSplit[] = imageName.split(".jpeg");
                    imageName = imageSplit[0] + ".jpg";
                }else
                imageName = imageName + ".jpg";
            }

            Picasso.with(context).load(Constants.IMAGE_URL +
                    imageName).
                    error(R.drawable.avatar).into(ivProfileImage);

        } else {
            if(user.getGender().equalsIgnoreCase("2")){
                ivProfileImage.setImageResource(R.drawable.avatar);
            }else{
                ivProfileImage.setImageResource(R.drawable.avatar);
            }

        }

        setPackadgeInfo(user.getPackage_item());
    }

    @Override
    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
    }

    public void setSideMenuInfo(float weight, int daysTrained) {
        tvGoal.setText(weight + "kg");
        tvDaysTrained.setText(daysTrained + "");
    }

    public void logout() {
        currentFragmentIndex = 0;
        userPrefManager.setLogoutUpdated(false);
        TemporaryProgressFragment.loadingViewShown = false;
        userPrefManager.clearPrefs(context);
        SharedPrefManager.resetAll(context);
        SharedPrefManager.removeTrainingPlanName(context);
        SharedPrefManager.removeTrainingLogsSync(context);
        SharedPrefManager.removeTrainingLogs(context);
        SharedPrefManager.removeCircuits(context);
        SharedPrefManager.removeExercises(context);
        SharedPrefManager.removeGraphInfo(context);
        SharedPrefManager.removeWorkouts(context);
        SharedPrefManager.removeMeals(context);
        SaveUserPreferences.removeUser(context);
        com.pttrackershared.utils.SaveUserPreferences.removeUser(context);
        com.pttrackershared.utils.Constants.user = null;
        TrainingPlanPreferences.removePlan(context);

        Intent i = new Intent(context, LoginActivity.class);
        i.putExtra("Logout","Logout");
        context.startActivity(i);
        this.finish();
    }

    @Override
    public void updateWeight(int weight) {
        if (User.getSelectedWeightUnit(context) == Constants.WEIGHT_UNIT_POUNDS) {
            weight = (int) AppUtils.convertPoundToKg(weight);
        }
        DialogUtils.ShowProgress(context, "Updating weight, Please wait.");

        RestApiManager.updateUserWeight(context, Integer.toString(weight), TimeUtils.toString(new Date()), new OnGetGraphInfoCompletionListener() {
            @Override
            public void onSuccess(GraphInfo graphInfo, List<GraphInfoItem> graphInfoItemList) {
                DialogUtils.HideDialog();
                SharedPrefManager.setUserStats(context, null);
                currentFragmentIndex = 0;
                showDefaultFragment();
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                DialogUtils.HideDialog();
                DialogUtils.ShowSnackbarAlert(context, errorMessage);
            }
        });
    }

    public void changeToolbarColor(int color) {
        toolbar.setBackgroundColor(getResources().getColor(color));
    }

    public static void setOverflowButtonColor(final Toolbar toolbar, final int color) {
        Drawable drawable = toolbar.getOverflowIcon();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), color);
            toolbar.setOverflowIcon(drawable);
        }
    }


    public void setPackadgeInfo(final int packageId) {

        if (packageId == 1) {
            clientMaxInt = 1;
            workoutMaxInt = 3;
            trainingMaxInt = 1;
            foodMaxInt = 0;
        } else if (packageId == 2) {
            clientMaxInt = 1;
            workoutMaxInt = 5;
            trainingMaxInt = 1;
            foodMaxInt = 0;
        } else if (packageId == 3) {
            clientMaxInt = 1;
            workoutMaxInt = 5;
            trainingMaxInt = 1;
            foodMaxInt = 0;
        } else if (packageId == 4) {
            clientMaxInt = 1;
            workoutMaxInt = 10;
            trainingMaxInt = 4;
            foodMaxInt = 0;
        } else if (packageId == 5) {
            clientMaxInt = 1;
            workoutMaxInt = 10;
            trainingMaxInt = 4;
            foodMaxInt = 0;
        } else if (packageId == 6) {
            clientMaxInt = 5;
            workoutMaxInt = 5;
            trainingMaxInt = 5;
            foodMaxInt = 1;
        } else if (packageId == 7) {
            clientMaxInt = 10;
            workoutMaxInt = 10;
            trainingMaxInt = 10;
            foodMaxInt = 2;
        } else if (packageId == 8) {
            clientMaxInt = 10;
            workoutMaxInt = 10;
            trainingMaxInt = 10;
            foodMaxInt = 2;
        } else if (packageId == 9) {
            clientMaxInt = 30;
            workoutMaxInt = 20;
            trainingMaxInt = 100;
            foodMaxInt = 30;
        } else if (packageId == 10) {
            clientMaxInt = 30;
            workoutMaxInt = 20;
            trainingMaxInt = 100;
            foodMaxInt = 30;
        } else if (packageId == 11) {
            clientMaxInt = 1;
            workoutMaxInt = 5;
            trainingMaxInt = 1;
            foodMaxInt = 0;
        } else if (packageId == 12) {
            clientMaxInt = 1;
            workoutMaxInt = 10;
            trainingMaxInt = 1;
            foodMaxInt = 0;
        } else if (packageId == 13) {
            clientMaxInt = 1;
            workoutMaxInt = 10;
            trainingMaxInt = 1;
            foodMaxInt = 0;
        } else if (packageId == 14) {
            clientMaxInt = 1;
            workoutMaxInt = 10;
            trainingMaxInt = 1;
            foodMaxInt = 0;
        } else if (packageId == 15) {
            clientMaxInt = 1;
            workoutMaxInt = 10;
            trainingMaxInt = 1;
            foodMaxInt = 0;
        } else if (packageId == 16) {
            clientMaxInt = 100;
            workoutMaxInt = 99;
            trainingMaxInt = 99;
            foodMaxInt = 100;
        }
        workoutMaxInt = 7;
//        workoutMax.setText("/" + workoutMaxInt + "");
//        workoutActive.setText(workoutActiveInt + "");
        trainingMaxInt = 1;
//        trainingMax.setText("/" + trainingMaxInt + "");
//        trainingActive.setText(0 + "");
        foodMaxInt = 1;
//        foodMax.setText("/" + foodMaxInt + "");
//        foodActive.setText(0 + "");

        if (SharedPrefManager.getWorkouts(context) != null){
            workoutsModel = new WorkoutsModel();
            workoutsModel = SharedPrefManager.getWorkouts(context);
            if (workoutsModel.workouts.size() == 0){
               TrainingPlan trainingPlanAdd = TrainingPlanPreferences.getPlan(context);
               if (trainingPlanAdd != null && trainingPlanAdd.getWorkoutList() != null)
               workoutsModel.workouts.addAll(trainingPlanAdd.getWorkoutList());
            }
        }

        ApiCallsHandler.getTrainingPlans(HomeActivity.this, new ListenersHandler.OnGetTrainingPlansCompletionListener() {
            @Override
            public void onSuccess(List<TrainingPlanJsonModel> trainingPlanListView) {
                if (trainingPlanListView.size() > 0){
                    List<TrainingPlan> trainingPlanList = com.pttrackershared.utils.AppUtils.saveTrainingPlans(HomeActivity.this,trainingPlanListView);
                    TrainingPlanPreferences.addPlan(trainingPlanList.get(0),HomeActivity.this);
                }

            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                Toast.makeText(HomeActivity.this, ""+errorMessage, Toast.LENGTH_SHORT).show();

            }
        });

        RestApiManager.getTrainingLogs(context, 1, new OnGetTrainingLogsCompletionListener() {
            @Override
            public void onSuccess(List<TrainingLog> trainingLogs, int pageNum, int pageCount) {
                trainingPlanActiveInt = trainingLogs.size();
                trainingLogsModel = new TrainingLogsModel();
                trainingLogsModel.trainingLogs.addAll(trainingLogs);
                SharedPrefManager.setTrainingLogs(HomeActivity.this,trainingLogsModel);
                RestApiManager.getWorkouts(context, new OnGetWorkoutsCompletionListener() {
                    @Override
                    public void onSuccess(List<Workout> workoutJsonModelList) {
                        workoutActiveInt = workoutJsonModelList.size();
                        workoutsModel = new WorkoutsModel();
                        workoutsModel.workouts.addAll(workoutJsonModelList);
                        if (workoutsModel.workouts.size() == 0){
                            TrainingPlan trainingPlanAdd = TrainingPlanPreferences.getPlan(context);
                            if (trainingPlanAdd != null && trainingPlanAdd.getWorkoutList() != null)
                            workoutsModel.workouts.addAll(trainingPlanAdd.getWorkoutList());
                        }
                        SharedPrefManager.setWorkouts(context,workoutsModel);
                        RestApiManager.getFoodList(context, new OnFoodListCompletionListener() {
                            @Override
                            public void onSuccess(List<Meals> userJsonModelList) {
                                foodActiveInt = userJsonModelList.size();
//                                foodActive.setText(1 + "");
                                if (userJsonModelList.size() > 0){
                                    MealsModel mealsModel = new MealsModel();
                                    mealsModel.model = (ArrayList<Meals>) userJsonModelList;
                                    SharedPrefManager.setMeals(HomeActivity.this,mealsModel);
                                    Date date = new Date();
                                    date.getTime();
                                    if (AppUtils.convertStringtoDate(userJsonModelList.get(0).getTrn_date_finish()).after(date)){
                                        Constants.foodplan = true;
                                    }else {
                                        Constants.foodplan = false;
                                    }
                                }else {
                                    Constants.foodplan = false;
                                }
                                RestApiManager.getExercises(context, new OnGetExerciseCompletionListener() {
                                    @Override
                                    public void onSuccess(List<Exercise> exerciseList) {
                                        ExercisesModel exercisesModel = new ExercisesModel();
                                        exercisesModel.exercises.addAll(exerciseList);
                                        SharedPrefManager.setExercises(context,exercisesModel);
                                        RestApiManager.getCircuits(context, new OnGetCircuitCompletionListener() {
                                            @Override
                                            public void onSuccess(List<Circuit> circuitList) {
                                                circuitsModel = new CircuitsModel();
                                                circuitsModel.circuits.addAll(circuitList);
                                                SharedPrefManager.setCircuits(context,circuitsModel);
                                                RestApiManager.getSingleTrainingPlan(context, new OnGetTrainingPlanListener() {
                                                    @Override
                                                    public void onSuccess(TrainingPlanView trainingPlanViewJsonModelList) {
                                                        SharedPrefManager.setTrainingPlanView(context,trainingPlanViewJsonModelList);
                                                    }

                                                    @Override
                                                    public void onFailure(int errorCode, String errorMessage) {
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onFailure(int errorCode, String errorMessage) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(int errorCode, String errorMessage) {
                                    }
                                });
                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {

                    }
                });
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
            }
        });
    }

    public void setDividerVisible(boolean visibile){
        if (visibile){
            dividerHomeScreen.setVisibility(View.VISIBLE);
        }else {
            dividerHomeScreen.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void networkAvailable() {
        BaseActivity.networkConnection = true;
        AppUtils.networkConnection = true;
        if (!BaseActivity.synced) {
            BaseActivity.synced = true;
        }
    }

    @Override
    public void networkUnavailable() {
        BaseActivity.networkConnection = false;
        AppUtils.networkConnection = false;
//        TemporaryProgressFragment.noConectionLayout.setVisibility(View.VISIBLE);
    }




    public long checkForExpiredTrial(){
        User user = SharedPrefManager.getUser(context);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        long differenceDays = 0;
        try {
            Date date1 = format.parse(user.getTrn_date());
            Date date2 = Calendar.getInstance().getTime();
            long difference = date2.getTime() - date1.getTime();
            differenceDays = difference / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (user.getPackage_item() != 1){
            differenceDays = 0;
        }

        return differenceDays;
    }

    @Override
    public void onSuccess(String image) {
        setUserInfo();
    }

    @Override
    public void onFailure(int errorCode, String errorMessage) {

    }

    @Override
    protected void onPause() {
        homeActivity = this;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        homeActivity = this;
        super.onDestroy();
    }
}

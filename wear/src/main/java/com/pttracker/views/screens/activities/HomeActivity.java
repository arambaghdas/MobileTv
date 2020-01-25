package com.pttracker.views.screens.activities;

import android.content.Intent;
import androidx.viewpager.widget.ViewPager;
import android.support.wearable.view.SwipeDismissFrameLayout;

import com.google.gson.Gson;
import com.pttracker.R;
import com.pttracker.adapters.SectionsPagerAdapter;
import com.pttracker.models.eventbus.HearRateChangedEvent;
import com.pttracker.services.HeartbeatService;
import com.pttracker.views.widgets.SwipeableViewPager;
import com.pttrackershared.models.TrainingLog;
import com.pttrackershared.models.Workout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * HomeActivity shows navigation viewpager and holds LandingFragment and ExerciseFragment.
 */
public class HomeActivity extends BaseActivity /*implements HeartbeatService.OnChangeListener*/ {

    //region View References

    @BindView(R.id.vp_home)
    public SwipeableViewPager vpHome;

    @BindView(R.id.sdfl_main)
    public SwipeDismissFrameLayout sdflMain;

    //endregion

    //region Other Variables

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Workout selectedWorkout;
    public boolean resultsPublished;
    private int totalTime;
    private long workoutId;
    private boolean dismissable;
    //    private static ServiceConnection serviceConnection;
    public static boolean isBinded;
    private int minBpm = -1;
    private int maxBpm;
    private boolean shouldReadBpm;
    private TrainingLog currentTrainingLog;

    private int steps;

    //endregion

    //region Controllers Variables
    //endregion

    //region Overridden Methods from Activity Base Class

    @Override
    public void onBackPressed() {
        if (dismissable) {
            if (vpHome.getCurrentItem() == 0) {
                super.onBackPressed();
            } else {
                showLandingFragment();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //endregion

    //region Basic Methods needed for Activity flow

    /**
     * Performs all operations right after creation of activity
     */
    @Override
    public void preStart() {
        super.preStart();
        setContentView(R.layout.activity_home);
        context = this;
        ButterKnife.bind(this);
    }

    /**
     * Initializes independently all data(not dependent on view or controller) variables to be used in this activity.
     */
    @Override
    public void initData() {
        super.initData();
        resultsPublished = false;
        dismissable = true;
        shouldReadBpm = false;
    }

    /**
     * Initializes independently all controllers (not dependent on view) to be used in this activity
     */
    @Override
    public void initControllers() {
        super.initControllers();
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
        vpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (resultsPublished && positionOffset == 0) {
//                    showResultActivity();
                    resultsPublished = false;
                    totalTime = 0;
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        sdflMain.addCallback(new SwipeDismissFrameLayout.Callback() {
            @Override
            public void onDismissed(SwipeDismissFrameLayout layout) {
                super.onDismissed(layout);
                finish();
            }
        });
    }

    public void showResultActivity() {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra(ResultActivity.KEY_TOTAL_TIME, totalTime);
        intent.putExtra(ResultActivity.KEY_TRAINING_LOG, new Gson().toJson(currentTrainingLog));
        intent.putExtra(ResultActivity.KEY_MIN_BPM, minBpm < 0 ? 0 : minBpm);
        intent.putExtra(ResultActivity.KEY_MAX_BPM, maxBpm < 0 ? 0 : maxBpm);
        intent.putExtra(ResultActivity.KEY_AVG_BPM, getAvgBpm() < 0 ? 0 : getAvgBpm());
        context.startActivity(intent);
    }

    /**
     * Performs activity specific business logic here after complete initialization of data, controllers and views.
     */
    @Override
    public void postStart() {
        super.postStart();
        initCollectionAdapters();
//        serviceConnection = new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName componentName, IBinder binder) {
//                ((HeartbeatService.HeartbeatServiceBinder) binder).setChangeListener(HomeActivity.this);
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName componentName) {
//
//            }
//        };
//        if (!HeartbeatService.IS_Bounded) {
        Intent intent = new Intent(context, HeartbeatService.class);
//            startService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
        startService(intent);
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
//    @Override
//    public void onStop() {
//        super.onStop();
//
////        if (HeartbeatService.IS_Bounded) {
////            unbindService(serviceConnection);
////            HeartbeatService.IS_Bounded = false;
////        }
//    }

    private void initCollectionAdapters() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        vpHome.setAdapter(mSectionsPagerAdapter);
    }

    public void setSwipeable(boolean swipeable) {
        vpHome.setSwipeable(swipeable);
        sdflMain.setDismissEnabled(true);
        dismissable = true;
    }

    public void setSwipeable(boolean swipeable, boolean dismissable) {
        vpHome.setSwipeable(swipeable);
        sdflMain.setDismissEnabled(dismissable);
        this.dismissable = dismissable;
    }

    public Workout getSelectedWorkout() {
        return selectedWorkout;
    }

    public void setSelectedWorkout(Workout selectedWorkout) {
        this.selectedWorkout = selectedWorkout;
    }

    public void showLandingFragment(int totalTime, long workoutId) {
        this.totalTime = totalTime;
        this.workoutId = workoutId;
        resultsPublished = true;
        ((HomeActivity) context).showResultActivity();
        //when result activity starts this homeActivity onPause will be called and landing fragment will be shown from onResume
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (resultsPublished)
            vpHome.setCurrentItem(0, true);
    }

    public void showLandingFragment() {
        vpHome.setCurrentItem(0, true);
    }

    //endregion
    //region Business Logic Specific to this Activity
    //endregion

    //region Callback Methods
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHeartRateChangedEvent(HearRateChangedEvent event) {

        if (event.getType().equalsIgnoreCase(HearRateChangedEvent.TYPE_HEART)) {
            int newValue = event.getBpm();

            if (!shouldReadBpm) {
                return;
            }

            if (minBpm == -1) {
                minBpm = newValue;
                maxBpm = newValue;
            }

            if (newValue < minBpm) {
                minBpm = newValue;
            } else if (newValue > maxBpm) {
                maxBpm = newValue;
            }
        }

        if(event.getType().equalsIgnoreCase(HearRateChangedEvent.TYPE_STEPS)){
            steps=event.getSteps();
        }

    }

    public void setShouldReadBpm(boolean shouldReadBpm) {
        this.shouldReadBpm = shouldReadBpm;
    }

    public void makeHeartRateValuesDefault() {
        minBpm = -1;
        maxBpm = 0;
    }

    public int getMinBpm() {
        return minBpm;
    }

    public int getMaxBpm() {
        return maxBpm;
    }

    public int getAvgBpm() {
        return (minBpm + maxBpm) / 2;
    }

    public TrainingLog getCurrentTrainingLog() {
        return currentTrainingLog;
    }

    public void setCurrentTrainingLog(TrainingLog currentTrainingLog) {
        this.currentTrainingLog = currentTrainingLog;
    }

    public int getSteps() {
        return steps;
    }


    //endregion
}




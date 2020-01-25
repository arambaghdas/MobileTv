package com.pttracker.trainingaid.activities;

import androidx.viewpager.widget.ViewPager;

import android.support.wearable.view.SwipeDismissFrameLayout;
import android.widget.ImageView;

import com.pttracker.trainingaid.R;
import com.pttracker.trainingaid.adapters.SectionsPagerAdapterExercise;
import com.pttracker.trainingaid.fragments.ExerciseFragment;
import com.pttracker.trainingaid.utils.CountDownTimerPausable;
import com.pttrackershared.views.widgets.SwipeableViewPager;

public class ExerciseActivity extends BaseActivity {

    public SwipeableViewPager vpHome;
    public SwipeDismissFrameLayout sdflMain;
    public static ImageView backButton;
    private SectionsPagerAdapterExercise mSectionsPagerAdapterExercise;
    private static ExerciseActivity exerciseActivity;
    public static CountDownTimerPausable timer;
    public long totalSeconds = 6000000;
    public long intervalSeconds = 1;
    public long start = 0;
    public static int restTimer = 0;

    @Override
    public void preStart() {
        super.preStart();
        setContentView(R.layout.activity_exercise);
        context = this;
        exerciseActivity = this;

        vpHome = (SwipeableViewPager) findViewById(R.id.vp_home);
        sdflMain = (SwipeDismissFrameLayout) findViewById(R.id.sdfl_main);
        backButton = (ImageView) findViewById(R.id.backButton);
        timer = new CountDownTimerPausable(totalSeconds * 1000, intervalSeconds * 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                millisUntilFinished = (totalSeconds * 1000 - millisUntilFinished) / 1000;
                restTimer = (int) millisUntilFinished;

                String ms = String.format("%02d:%02d:%02d", millisUntilFinished / 3600,
                        (millisUntilFinished % 3600) / 60, (millisUntilFinished % 60));
                ExerciseFragment.tvElapsedTime.setText(ms);
            }

            @Override
            public void onFinish() {

            }
        };
    }

    private void initCollectionAdapters() {
        mSectionsPagerAdapterExercise = new SectionsPagerAdapterExercise(getSupportFragmentManager());
        vpHome.setOffscreenPageLimit(1);
        vpHome.setAdapter(mSectionsPagerAdapterExercise);
    }

    @Override
    public void initListeners() {
        super.initListeners();
        vpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        sdflMain.setDismissEnabled(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        vpHome.setCurrentItem(0);

    }

    @Override
    protected void onResume() {
        super.onResume();
        vpHome.setCurrentItem(0);
        exerciseActivity = this;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void postStart() {
        super.postStart();
        initCollectionAdapters();
    }

    @Override
    protected void onPause() {
        super.onPause();
        exerciseActivity = this;
    }


    public static void finishExerciseActivity(){
        exerciseActivity.finish();
    }

    public static ExerciseActivity getInstance(){
       return exerciseActivity;
    }

}


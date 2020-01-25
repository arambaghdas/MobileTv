package com.pttracker.trainingaid.Class;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.support.wearable.view.SwipeDismissFrameLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pttracker.trainingaid.R;
import com.pttracker.trainingaid.adapters.SectionsPagerAdapter;
import com.pttracker.trainingaid.fragments.BaseFragment;
import com.pttracker.trainingaid.plugins.DialogUtils;
import com.pttrackershared.views.widgets.SwipeableViewPager;
import com.pttrackershared.models.eventbus.Workout;

import me.relex.circleindicator.CircleIndicator;

public class TrainingAid extends BaseFragment {

    public static SwipeableViewPager vpTrainingAid;
    public CircleIndicator home_indicator;
    public SwipeDismissFrameLayout sdflMain;
    public static Workout workout;
    public static String heartRate;
    public static Context context;
    public static TrainingAid currentFragment;
    public static boolean removedFirst = false;
    public boolean resultsPublished;
    private int totalTime;
    public static Workout selectedWorkout;
    public static boolean restartHome = false;


    @Override
    public void preStart(LayoutInflater inflater, @Nullable ViewGroup container) {
        super.preStart(inflater, container);
        rootView = inflater.inflate(R.layout.fragment_training_aid2, container, false);

        setHasOptionsMenu(true);

        context = getActivity();

        vpTrainingAid = rootView.findViewById(R.id.vp_training_aid2);
        home_indicator = rootView.findViewById(R.id.home_indicator);
        sdflMain = (SwipeDismissFrameLayout) rootView.findViewById(R.id.sdfl_main);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void initViews() {

        super.initViews();
    }

    @Override
    public void initControllers() {
        super.initControllers();
    }

    @Override
    public void postStart() {
        super.postStart();

        SensorManager sensorMngr = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorMngr.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        if (sensor == null) {
            heartRate = "N/A";
        } else if (sensor!=null){
        }

//        vpTrainingAid.setAdapter(new SectionsPagerAdapter(getFragmentManager()));
//        vpTrainingAid.setCurrentItem(0);
        setUpAdapter();
    }

    public void setUpAdapter(){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getFragmentManager(),false);
        vpTrainingAid.setAdapter(adapter);

    }

    public static TrainingAid newInstance() {
        return new TrainingAid();
    }

    public static TrainingAid getInstance() {
        return  currentFragment;
    }

    public void showLandingFragment() {
        vpTrainingAid.setCurrentItem(0, true);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        DialogUtils.HideDialog();
    }
    public void initCollectionAdapters(boolean removedFirst) {
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager(), removedFirst);
        vpTrainingAid.setAdapter(mSectionsPagerAdapter);
        vpTrainingAid.setOffscreenPageLimit(4);
        home_indicator.setVisibility(View.INVISIBLE);
        home_indicator.setViewPager(vpTrainingAid);
    }


    @Override
    public void initListeners() {
        super.initListeners();
        vpTrainingAid.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                if (position == 0) {
                    sdflMain.setDismissEnabled(true);
                    home_indicator.setVisibility(View.INVISIBLE);
                    if (removedFirst) {
                        sdflMain.setDismissEnabled(false);
                        home_indicator.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (!removedFirst) {
                        removedFirst = true;
                        initCollectionAdapters(removedFirst);
                        vpTrainingAid.setCurrentItem(2);
                    }
                    sdflMain.setDismissEnabled(false);
                    home_indicator.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        sdflMain.addCallback(new SwipeDismissFrameLayout.Callback() {
            @Override
            public void onDismissed(SwipeDismissFrameLayout layout) {
                super.onDismissed(layout);
                getActivity().finish();
            }
        });
    }
}





package com.pttracker.views.screens.activities;

import androidx.viewpager.widget.ViewPager;
import android.support.wearable.view.SwipeDismissFrameLayout;

import com.google.gson.Gson;
import com.pttracker.R;
import com.pttracker.adapters.ZonePagerAdapter;
import com.pttracker.views.widgets.SwipeableViewPager;
import com.pttrackershared.models.TrainingLog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ResultActivity shows time to complete recent workout. Gets value for completed time from activity starting this activity
 */
public class ResultActivity extends BaseActivity {
    //region Other Variables
    public static final String KEY_TOTAL_TIME = "key_total_time";
    public static final String KEY_TRAINING_LOG = "training_log";
    public static final String KEY_MIN_BPM = "key_min_bpm";
    public static final String KEY_MAX_BPM = "key_max_bpm";
    public static final String KEY_AVG_BPM = "key_avg_bpm";
    static public int totalTime;
    static public TrainingLog trainingLog;
    static public long minBpm;
    static public long maxBpm;
    static public long avgBpm;

    @BindView(R.id.vp_home)
    public SwipeableViewPager vpHome;

    @BindView(R.id.sdfl_main)
    public SwipeDismissFrameLayout sdflMain;
    public boolean resultsPublished;
    ZonePagerAdapter zonePagerAdapter;

    @Override
    public void preStart() {
        super.preStart();
        setContentView(R.layout.activity_result);
        context = this;
        ButterKnife.bind(this);
    }

    /**
     * Initializes independently all data(not dependent on view or controller) variables to be used in this activity.
     */
    @Override
    public void initData() {
        super.initData();
        totalTime = getIntent().getExtras().getInt(KEY_TOTAL_TIME, 0);
        if (getIntent().getSerializableExtra(KEY_TRAINING_LOG) != null)
            trainingLog = new Gson().fromJson(getIntent().getStringExtra(KEY_TRAINING_LOG), TrainingLog.class);
        minBpm = getIntent().getIntExtra(KEY_MIN_BPM, 0);
        maxBpm = getIntent().getIntExtra(KEY_MAX_BPM, 0);
        avgBpm = getIntent().getIntExtra(KEY_AVG_BPM, 0);
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

        vpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (resultsPublished && positionOffset == 0) {
                    resultsPublished = false;
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

    /**
     * Performs activity specific business logic here after complete initialization of data, controllers and views.
     */

    @Override
    public void postStart() {
        super.postStart();
        initCollectionAdapters();
    }

    private void initCollectionAdapters() {
        zonePagerAdapter = new ZonePagerAdapter(getFragmentManager());
        vpHome.setAdapter(zonePagerAdapter);
    }

    public void setSwipeable(boolean swipeable) {
        vpHome.setSwipeable(swipeable);
        sdflMain.setDismissEnabled(true);
    }

    public void setSwipeable(boolean swipeable, boolean dismissable) {
        vpHome.setSwipeable(swipeable);
        sdflMain.setDismissEnabled(dismissable);
    }
}




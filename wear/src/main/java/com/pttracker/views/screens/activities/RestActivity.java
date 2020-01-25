package com.pttracker.views.screens.activities;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pttracker.R;
import com.pttracker.datamanagers.ExerciseImagesDataManager;
import com.pttracker.datamanagers.UserAccountDataManager;
import com.pttracker.models.eventbus.HearRateChangedEvent;
import com.pttrackershared.models.Exercise;
import com.pttrackershared.models.User;
import com.pttrackershared.plugins.TimeUtils;
import com.pttrackershared.plugins.ValidatorUtils;
import com.pttrackershared.utils.Constants;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ResultActivity shows time to complete recent workout. Gets value for completed time from activity starting this activity
 */
public class RestActivity extends BaseActivity {

    //region View References
    @BindView(R.id.tv_heart_rate)
    TextView tvHeartRate;

    @BindView(R.id.tv_exercise_name)
    TextView tvExerciseName;

    @BindView(R.id.iv_exercise_image)
    ImageView ivExerciseImage;

    @BindView(R.id.tv_sets_count)
    TextView tvSetsCount;

    @BindView(R.id.tv_weight)
    TextView tvWeight;

    @BindView(R.id.tv_count_down)
    TextView tvCountDown;

    //endregion

    //region Other Variables
    public static final String SELECTED_EXERCISE = "selected_exercise";
    public static Exercise selectedExercise;
    private Handler handler;
    private long startTime;
    private long waitTime;
    //endregion

    //region Controllers Variables
    //endregion

    //region Overridden Methods from Activity Base Class

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
    //endregion

    //region Basic Methods needed for Activity flow

    /**
     * Performs all operations right after creation of activity
     */
    @Override
    public void preStart() {
        super.preStart();
        setContentView(R.layout.activity_rest);
        context = this;

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                ButterKnife.bind(RestActivity.this);
//                selectedExercise = getIntent().getExtras().getParcelable(SELECTED_EXERCISE);
                SensorManager sensorMngr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                Sensor sensor = sensorMngr.getDefaultSensor(Sensor.TYPE_HEART_RATE);
                if (sensor == null) {
                    tvHeartRate.setText("N/A");
                }
                startTime = new Date().getTime();
                if (selectedExercise.getRestTime() <= 10) {
                    waitTime = 11000;//11 seconds default rest time
                } else {
                    waitTime = selectedExercise.getRestTime() * 1000;
                }
                showExerciseInfo();
                handler.post(waitRunnable);
            }
        });
    }

    /**
     * Initializes independently all data(not dependent on view or controller) variables to be used in this activity.
     */
    @Override
    public void initData() {
        super.initData();


    }

    /**
     * Initializes independently all controllers (not dependent on view) to be used in this activity
     */
    @Override
    public void initControllers() {
        super.initListeners();
        handler = new Handler();
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
    }

    /**
     * Performs activity specific business logic here after complete initialization of data, controllers and views.
     */
    @Override
    public void postStart() {
        super.postStart();
    }

    //endregion
    //region Business Logic Specific to this Activity
    private void showExerciseInfo() {
        tvExerciseName.setText(selectedExercise.getName());
        if (ivExerciseImage != null && ExerciseImagesDataManager.getInstance(context).hasImage(selectedExercise.getExerciseId())) {
            ivExerciseImage.setImageBitmap(ExerciseImagesDataManager.getInstance(context).
                    getExerciseImage(selectedExercise.getExerciseId()).getImageBitmap());
        } else {
//            ivExerciseImage.setImageResource(R.drawable.error_image);
            String imageName = selectedExercise.getImageName();
            User user = UserAccountDataManager.getInstance(context).getCurrentUser();
            if (user.getGender().equalsIgnoreCase("2")) {
                Picasso.with(context).load(Constants.IMAGE_URL_FEMALE +
                        imageName).
                        error(R.drawable.error_image).into(ivExerciseImage);
            } else {
                Picasso.with(context).load(Constants.IMAGE_URL_MALE +
                        imageName).
                        error(R.drawable.error_image).into(ivExerciseImage);
            }
        }
        if (selectedExercise.getDuration() != 0) {
            tvSetsCount.setText("Time:" + TimeUtils.getTimeString(selectedExercise.getDuration()));
        } else
            tvSetsCount.setText(selectedExercise.getReps() + " Reps");


        if (selectedExercise.getWeight() > 0)
            tvWeight.setText(selectedExercise.getWeight() + " KG");
        else
            tvWeight.setText(" - ");
    }

    private Runnable waitRunnable = new Runnable() {
        @Override
        public void run() {
            long targetTime = startTime + waitTime;
            if (System.currentTimeMillis() >= targetTime - 10000) {
                goBack();
                handler.removeCallbacks(waitRunnable);
            } else {
                int diff = (int) (targetTime - System.currentTimeMillis()) / 1000;
                tvCountDown.setText(Integer.toString(diff));
                handler.postDelayed(waitRunnable, 500);
            }
        }
    };

    private void goBack() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }
    //endregion

    //region Callback Methods
    @OnClick(R.id.iv_cancel)
    public void onCancelClicked(View view) {
        handler.removeCallbacks(waitRunnable);
        goBack();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHeartRateChangedEvent(HearRateChangedEvent hearRateChangedEvent) {
        tvHeartRate.setText(hearRateChangedEvent.getBpm() + "");
    }
    //endregion

}




package com.pttracker.views.screens.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.wearable.view.WatchViewStub;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ResultActivity shows time to complete recent workout. Gets value for completed time from activity starting this activity
 */
public class GetReadyActivity extends BaseActivity {

    //region View References
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_cancel)
    ImageView btnCancel;

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


    @BindView(R.id.tv_distance)
    TextView tvDistance;

    @BindView(R.id.view_count_down)
    View viewCountDown;

    @BindView(R.id.tv_count_down)
    TextView tvCountDown;

    @BindView(R.id.tv_go)
    TextView tvGo;


    //endregion

    //region Other Variables
    public static final String SELECTED_EXERCISE = "selected_exercise";
    public static final String COUNTDOWN = "countdown";
    public static final String SHOW_CANCEL = "showCancel";
    public static Exercise selectedExercise;
    public static boolean showCancel = true;
    private Handler handler;
    private long startTime;
    private long waitTime = 1000 * 11;
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
        setContentView(R.layout.activity_get_ready);
        context = this;


        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                ButterKnife.bind(GetReadyActivity.this);

                SensorManager sensorMngr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                Sensor sensor = sensorMngr.getDefaultSensor(Sensor.TYPE_HEART_RATE);
                if (sensor == null) {
                    tvHeartRate.setText("N/A");
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
        try {
            startTime = System.currentTimeMillis();
//            selectedExercise = getIntent().getExtras().getParcelable(SELECTED_EXERCISE);
//            showCancel = getIntent().getExtras().getBoolean(SHOW_CANCEL);
            waitTime = (getIntent().getExtras().getInt(COUNTDOWN) + 1) * 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
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

//        if (selectedExercise.getWeight() > 0)
//            tvWeight.setText(selectedExercise.getWeight() + " KG");
//        else
//            tvWeight.setText(" - ");

        if (selectedExercise.getWeight() == 0) {
            tvWeight.setText("Custom");
            tvWeight.setBackgroundColor(context.getResources().getColor(R.color.purple));
        } else if (selectedExercise.getWeight() == 1) {
            tvWeight.setText("Low");
            tvWeight.setBackgroundColor(context.getResources().getColor(R.color.green));
        } else if (selectedExercise.getWeight() == 2) {
            tvWeight.setText("Medium");
            tvWeight.setBackgroundColor(context.getResources().getColor(R.color.yellow));
        } else if (selectedExercise.getWeight() == 3) {
            tvWeight.setText("High");
            tvWeight.setBackgroundColor(context.getResources().getColor(R.color.red));
        } else if (selectedExercise.getWeight() > 3) {
            tvWeight.setBackgroundColor(context.getResources().getColor(R.color.grey_txt));
            tvWeight.setText(selectedExercise.getWeight() + " KG");
        }

        if (tvDistance != null) {
            tvDistance.setText("" + selectedExercise.getDistance());
        }

        if (showCancel) {
            btnCancel.setVisibility(View.VISIBLE);
        } else {
            btnCancel.setVisibility(View.INVISIBLE);
        }

        viewCountDown.setVisibility(View.VISIBLE);
        tvGo.setVisibility(View.GONE);
    }

    private void showGoView() {
        viewCountDown.setVisibility(View.GONE);
        tvTitle.setVisibility(View.INVISIBLE);
        tvGo.setVisibility(View.VISIBLE);
    }

    private Runnable waitRunnable = new Runnable() {
        @Override
        public void run() {
            long targetTime = startTime + waitTime;
            int diff = (int) (targetTime - System.currentTimeMillis()) / 1000;
            tvCountDown.setText(Integer.toString(diff));
            if (System.currentTimeMillis() >= targetTime - 1000) {
                showGoView();
                handler.removeCallbacks(this);
                handler.postDelayed(goRunnable, 800);
            } else {
                handler.postDelayed(waitRunnable, 500);
            }
        }
    };

    private Runnable goRunnable = new Runnable() {
        @Override
        public void run() {
            goBack();
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
        handler.removeCallbacks(goRunnable);
        handler.removeCallbacks(waitRunnable);
        goBack();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHeartRateChangedEvent(HearRateChangedEvent hearRateChangedEvent) {
        tvHeartRate.setText(hearRateChangedEvent.getBpm() + "");
    }


    //endregion

}




package com.pttracker.trainingaid.Class;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.pttracker.trainingaid.R;
import com.pttracker.trainingaid.activities.BaseActivity;
import com.pttracker.trainingaid.plugins.DialogUtils;
import com.pttracker.trainingaid.utils.SaveUserPreferences;
import com.pttracker.trainingaid.models.GraphInfo;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.User;
import com.pttrackershared.models.eventbus.Workout;
import com.pttrackershared.plugins.ValidatorUtils;
import com.pttrackershared.utils.Constants;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrainingAidFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrainingAidFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrainingAidFragment extends BaseActivity{

    public TrainingAidFragment() {
    }

        DrawerLayout drawerLayout;
        GraphInfo graphInfo;
        ImageView ivProfileImage;
        TextView tvFullName;
        TextView tvGoal;
        TextView tvDaysTrained;
        private Fragment currentFragment;
        private boolean isBackPressedOnce;
        private static int currentFragmentIndex;
        private static final int SETTING_REQUEST_CODE = 1221;

        private Handler backPressedHandler = new Handler();
        private Runnable backRunnable = new Runnable() {
            @Override
            public void run() {
                isBackPressedOnce = false;
            }
        };
        public boolean isVisible;
        TrainingAid trainingAid;

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == SETTING_REQUEST_CODE) {
                recreate();
            }
        }

        @Override
        protected void onResume() {
            super.onResume();
            setUserInfo();
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
                super.onBackPressed();
            }
        }



        private void setUserInfo() {
            User user = SaveUserPreferences.getUser(context);
            tvFullName.setText(user.getFirstname() + " " + user.getSurname());
            if (!ValidatorUtils.IsNullOrEmpty(user.getImage())) {
                Picasso.with(context).load(Constants.IMAGE_URL + user.getImage()).error(R.drawable.ic_profile_avatar).into(ivProfileImage);
            } else {
                ivProfileImage.setImageResource(R.drawable.ic_profile_avatar);
            }

            setTitle(user.getFirstname() + " " + user.getSurname());
        }

        private Workout selectedWorkout;
        public static boolean resultsPublished;
        private int totalTime;
        private long workoutId;
        private boolean dismissable;
        //    private static ServiceConnection serviceConnection;
        public static boolean isBinded;
        private int minBpm = -1;
        private int maxBpm;
        private boolean shouldReadBpm;
        private TrainingLog currentTrainingLog;


    public Workout getSelectedWorkout() {
        return selectedWorkout;
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
    public void setCurrentTrainingLog(TrainingLog currentTrainingLog) {
        this.currentTrainingLog = currentTrainingLog;
    }

    public void setShouldReadBpm(boolean shouldReadBpm) {
        this.shouldReadBpm = shouldReadBpm;
    }

    public void makeHeartRateValuesDefault() {
        minBpm = -1;
        maxBpm = 0;
    }
    public void setSwipeable(boolean swipeable) {
        //vpHome.setSwipeable(swipeable);
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
    public void initListeners() {
        super.initListeners();
    }


    public void setSelectedWorkout(Workout selectedWorkout) {
        this.selectedWorkout = selectedWorkout;
    }
    //endregion

    public static TrainingAidFragment newInstance() {
        return new TrainingAidFragment();
    }

    public void showLandingFragment(int totalTime, long workoutId) {
        this.totalTime = totalTime;
        this.workoutId = workoutId;
        resultsPublished = true;
        showResultActivity();
        //when result activity starts this homeActivity onPause will be called and landing fragment will be shown from onResume
    }

    public void showResultActivity() {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        DialogUtils.HideDialog();
    }
    // TODO: Rename method, update argument and hook method into UI event

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}

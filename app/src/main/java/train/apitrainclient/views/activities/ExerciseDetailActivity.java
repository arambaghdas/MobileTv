package train.apitrainclient.views.activities;

import com.pttrackershared.views.widgets.SwipeableViewPager;

import butterknife.BindView;
import train.apitrainclient.R;
import train.apitrainclient.adapters.ExerciseTabsPagerAdapter;
import train.apitrainclient.views.fragments.ExerciseExerciseTabFragment;

public class ExerciseDetailActivity extends BaseActivity {

    @BindView(R.id.vp_home)
    SwipeableViewPager vp_home;

    public static final String SELECTED_EXERCISE = "selected_exercise";

    @Override
    public void onBackPressed() {
        if (ExerciseExerciseTabFragment.isFullScreen) {
            ExerciseExerciseTabFragment.youTubePlayer.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void preStart() {
        super.preStart();
        setContentView(R.layout.content_exercise_detail);
        context = this;
    }

    @Override
    public void initData() {
        super.initData();
    }


    public void setUpViews(){
        ExerciseTabsPagerAdapter exerciseTabsPagerAdapter = new ExerciseTabsPagerAdapter(getSupportFragmentManager());
        vp_home.setSwipeable(false);
        vp_home.setAdapter(exerciseTabsPagerAdapter);
        vp_home.setCurrentItem(0);
    }

    @Override
    public void initControllers() {
        super.initListeners();
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void initListeners() {
        super.initListeners();
    }

    @Override
    public void postStart() {
        super.postStart();
        setUpViews();
    }
}

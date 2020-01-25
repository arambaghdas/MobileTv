package train.apitrainclient.views.activities;

import android.support.wearable.view.SwipeDismissFrameLayout;

import androidx.viewpager.widget.ViewPager;

import com.pttrackershared.views.widgets.SwipeableViewPager;

import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import train.apitrainclient.R;
import train.apitrainclient.adapters.LoginPagerAdapterExercise;
import train.apitrainclient.receivers.NetworkStateReceiver;

public class LoginActivity extends BaseActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    public SwipeableViewPager vpHome;
    public SwipeDismissFrameLayout sdflMain;
    CircleIndicator home_indicator;
    public static String email = "";
    public static int flagIntro = 0;
    NetworkStateReceiver networkStateReceiver;

    @Override
    public void preStart() {
        super.preStart();
        setContentView(R.layout.login_activity);

        ButterKnife.bind(this);

        vpHome = (SwipeableViewPager) findViewById(R.id.vp_home);
        vpHome.setSwipeable(true);
        sdflMain = (SwipeDismissFrameLayout) findViewById(R.id.sdfl_main);
        sdflMain.setSwipeable(true);
        home_indicator = (CircleIndicator) findViewById(R.id.home_indicator);

    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initControllers() {
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
    protected void onResume() {
        super.onResume();
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
        initCollectionAdapters();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (OnboardingWithCenterAnimationActivity.networkConnection)
        if (networkStateReceiver != null) {
            networkStateReceiver.removeListener(this);
            this.unregisterReceiver(networkStateReceiver);
        }
    }

    @Override
    public void onBackPressed() {
    }

    public void initCollectionAdapters() {
        LoginPagerAdapterExercise mSectionsPagerAdapterExercise = new LoginPagerAdapterExercise(getSupportFragmentManager());
        vpHome.setAdapter(mSectionsPagerAdapterExercise);
        vpHome.setOffscreenPageLimit(3);
        home_indicator.setViewPager(vpHome);
        if (getIntent().hasExtra("Logout")){
            vpHome.setCurrentItem(3);
        }else
            vpHome.setCurrentItem(0);
    }

    @Override
    public void networkAvailable() {
    }

    @Override
    public void networkUnavailable() {

    }


}

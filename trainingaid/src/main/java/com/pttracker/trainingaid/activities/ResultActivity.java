package com.pttracker.trainingaid.activities;

import androidx.viewpager.widget.ViewPager;
import android.support.wearable.view.SwipeDismissFrameLayout;
import android.widget.ImageView;
import com.pttracker.trainingaid.R;
import com.pttracker.trainingaid.adapters.SectionsPagerAdapterResult;
import com.pttrackershared.views.widgets.SwipeableViewPager;

/**
 * ResultActivity shows time to complete recent workout. Gets value for completed time from activity starting this activity
 */
public class ResultActivity extends BaseActivity {

    public SwipeableViewPager vpHome;
    public SwipeDismissFrameLayout sdflMain;
    public static ImageView backButton;
    public static ResultActivity resultActivity;
    //endregion

    //region Other Variables
    private SectionsPagerAdapterResult mSectionsPagerAdapterResult;

    /**
     * Performs all operations right after creation of activity
     */
    @Override
    public void preStart() {
        super.preStart();
        setContentView(R.layout.activity_result);
        context = this;
        resultActivity = this;
        vpHome = (SwipeableViewPager)findViewById(R.id.vp_home);
        sdflMain = (SwipeDismissFrameLayout)findViewById(R.id.sdfl_main);
        backButton = (ImageView) findViewById(R.id.backButton);
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
     * Performs activity specific business logic here after complete initialization of data, controllers and views.
     */
    @Override
    public void postStart() {
        super.postStart();
        initCollectionAdapters();
    }

    private void initCollectionAdapters() {
        mSectionsPagerAdapterResult = new SectionsPagerAdapterResult(getSupportFragmentManager());
        vpHome.setAdapter(mSectionsPagerAdapterResult);
        sdflMain.setDismissEnabled(false);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        resultActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        resultActivity = this;
    }

    public static void finishResultActivity(){
        resultActivity.finish();
    }

    public static ResultActivity getInstance(){return resultActivity;}

}




package train.apitrainclient.app;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;

import pl.tajchert.nammu.Nammu;
import com.pttrackershared.models.eventbus.User;
import train.apitrainclient.plugins.LocaleHelper;
import train.apitrainclient.utils.LoggerUtils;
import train.apitrainclient.views.activities.OnboardingWithCenterAnimationActivity;

public class PTTrackerApp extends MultiDexApplication {

    public Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        preStart();
        initData();
        initControllers();
        initListeners();
        postStart();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, User.getSelectedLanguageCode(base)));
    }

    private void preStart() {
        context = this;
    }

    private void initData() {
    }


    private void initControllers() {
        if (OnboardingWithCenterAnimationActivity.networkConnection){
        Nammu.init(context);
        MultiDex.install(context);
        Stetho.initializeWithDefaults(context);
        }
    }

    private void initListeners() {
    }

    private void postStart() {
        LoggerUtils.d("test");
    }
}




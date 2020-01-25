package train.apitrainclient.views.activities;

/**
 * Created by marcjesus on 05/10/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paginate.Paginate;

import retrofit2.Response;
import train.apitrainclient.R;
import com.pttrackershared.models.eventbus.UpdatePasswordModel;
import com.pttrackershared.models.eventbus.User;
import train.apitrainclient.networks.retrofit.Api;
import train.apitrainclient.networks.retrofit.ResponseHandler;
import train.apitrainclient.utils.AppUtils;
import train.apitrainclient.utils.SharedPrefManager;
import train.apitrainclient.utils.UserPrefManager;

public class OnboardingWithCenterAnimationActivity extends AppCompatActivity implements Paginate.Callbacks {
    public static final int STARTUP_DELAY = 300;
    public static final int ANIM_ITEM_DURATION = 1000;
    public static final int ITEM_DELAY = 300;

    private boolean animationStarted = false;

    private Button loginBtn;
    private Button signupBtn;
    private boolean isBackPressedOnce;
    private Handler backPressedHandler = new Handler();
    private Context context = this;
    public UserPrefManager userPrefManager;

    public static  OnboardingWithCenterAnimationActivity splashActivity;
    private Runnable backRunnable = new Runnable() {
        @Override
        public void run() {
            isBackPressedOnce = false;
        }
    };
    public static boolean networkConnection = true;
    public static boolean recreated = false;
    public static boolean synced = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_CenterAnimation);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_onboarding_center);
        splashActivity = this;

        loginBtn = (Button) findViewById(R.id.splash_btn);
        signupBtn = (Button) findViewById(R.id.splash_btn2);

        TextView tvVersion = (TextView) findViewById(R.id.versionApp);
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            tvVersion.setText(info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        loginBtn.setVisibility(View.INVISIBLE);
        signupBtn.setVisibility(View.INVISIBLE);
        userPrefManager = new UserPrefManager(this);
        if (isNetworkConnected() && internetIsConnected()){
            networkConnection = true;
            AppUtils.networkConnection = true;
            checkLoggedInState();
        }else {
            networkConnection = false;
            AppUtils.networkConnection = false;
            SharedPrefManager.getUser(OnboardingWithCenterAnimationActivity.this);
            User user = SharedPrefManager.getUser(OnboardingWithCenterAnimationActivity.this);
            if (user.getAccessToken() != null){
                moveToHomeScreen();
            }else {
                moveToLoginScreen();
            }
        }


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToLoginScreen();
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToSignupScreen();
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus || animationStarted) {
            return;
        }

        animate();

        super.onWindowFocusChanged(hasFocus);
    }

    private void animate() {
        ImageView logoImageView = (ImageView) findViewById(R.id.img_logo);
        ViewGroup container = (ViewGroup) findViewById(R.id.container);

        ViewCompat.animate(logoImageView)
                .translationY(-250)
                .setStartDelay(STARTUP_DELAY)
                .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                new DecelerateInterpolator(1.2f)).start();

        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator;

            if (!(v instanceof Button)) {
                viewAnimator = ViewCompat.animate(v)
                        .translationY(50).alpha(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(1000);
            } else {
                viewAnimator = ViewCompat.animate(v)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(500);
            }

            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
        }
    }

    private void moveToHomeScreen() {
        Intent i = new Intent(context, HomeActivity.class);
        startActivity(i);
        finish();

    }

    private void moveToLoginScreen() {
        startActivity(new Intent(context, LoginActivity.class));
        finish();
    }

    private void moveToSignupScreen() {
//        startActivity(new Intent(context, SignUpActivity.class));
//        finish();
    }


    private void checkLoggedInState() {
        if (userPrefManager.isLoggedIn()) {
            UpdatePasswordModel updatePasswordModel = new UpdatePasswordModel();
            User user = SharedPrefManager.getUser(context);
            updatePasswordModel.client_mobile = user.getTelephone();

            Api.getApiService(this).updateToken(updatePasswordModel.client_mobile).enqueue(new ResponseHandler<User>() {
                @Override
                public void onSuccess(Response<User> response) {
                    user.setAccessToken(response.body().getAccessToken());
                    userPrefManager.saveAccessToken(user.getAccessToken());
                    moveToHomeScreen();
                }

                @Override
                public void onFailure(int errorCode, String errorMessage) {
                    Toast.makeText(context, ""+"Network error, please log in", Toast.LENGTH_SHORT).show();
                    moveToLoginScreen();

                }
            });
        }else {
            moveToLoginScreen();
        }
    }


    @Override
    public void onLoadMore() {

    }

    @Override
    public boolean isLoading() {
        return false;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        splashActivity = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        splashActivity = this;
    }

    @Override
    public void onBackPressed() {
        if (!isBackPressedOnce) {
            isBackPressedOnce = true;
            backPressedHandler.postDelayed(backRunnable, 3000);
            Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
        } else {
            OnboardingWithCenterAnimationActivity.splashActivity.finish();

            super.onBackPressed();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }
}

package train.apitrainclient.views.activities;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import train.apitrainclient.R;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_version)
    TextView tvVersion;

    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;

    @BindView(R.id.weblink)
    TextView weblink;

    @BindColor(R.color.white)
    int colorBlack;

    @BindDrawable(R.drawable.ic_custom_back_blue)
    Drawable drUpArrow;

    @Override
    public void preStart() {
        super.preStart();
        setContentView(R.layout.activity_about);
        context = this;
    }

    @Override
    public void initData() {
        super.initData();
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
        setupToolbar();
        showAppInfo();

        weblink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://" + weblink.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW); i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(drUpArrow);
    }

    private void showAppInfo(){
        setVersion();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);

        tvReleaseDate.setText(formattedDate);
    }

    private void setVersion(){
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            tvVersion.setText(info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}


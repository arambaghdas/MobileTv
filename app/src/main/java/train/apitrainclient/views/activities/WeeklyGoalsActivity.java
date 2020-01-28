package train.apitrainclient.views.activities;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.pttrackershared.models.eventbus.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import train.apitrainclient.R;
import train.apitrainclient.utils.SharedPrefManager;

public class WeeklyGoalsActivity extends AppCompatActivity {

    @BindView(R.id.sp_height)
    Spinner spHeight;

    @BindView(R.id.sp_weight)
    Spinner spWeight;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_goals);
        ButterKnife.bind(this);
        user = SharedPrefManager.getUser(this);
        createSpinners();
    }

    public void createSpinners() {
        ArrayList<String> weights = new ArrayList<>();
        weights.add(getString(R.string.metric_to_kg));
        weights.add(getString(R.string.metric_to_pound));

        ArrayList<String> heights = new ArrayList<>();
        heights.add(getString(R.string.metric_to_cm));
        heights.add(getString(R.string.metric_to_feet));

        ArrayAdapter<String> spinner1Adapter = new ArrayAdapter<String>(WeeklyGoalsActivity.this,
                R.layout.spinner_weekly_goals, weights);
        spWeight.setAdapter(spinner1Adapter);

        ArrayAdapter<String> spinner2Adapter = new ArrayAdapter<String>(WeeklyGoalsActivity.this,
                R.layout.spinner_weekly_goals, heights);
        spHeight.setAdapter(spinner2Adapter);
    }

    @OnClick(R.id.rl_back)
    public void onBackPress() {
        onBackPressed();
    }

}



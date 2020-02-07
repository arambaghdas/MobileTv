package train.apitrainclient.views.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.pttrackershared.models.eventbus.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import train.apitrainclient.R;
import train.apitrainclient.databinding.ActivityWeeklyGoalsBinding;
import train.apitrainclient.utils.SharedPrefManager;

public class WeeklyGoalsActivity extends AppCompatActivity {

    @BindView(R.id.sp_height)
    Spinner spHeight;

    @BindView(R.id.sp_weight)
    Spinner spWeight;

    User user;
    private ActivityWeeklyGoalsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weekly_goals);
        ButterKnife.bind(this);

        user = SharedPrefManager.getUser(this);
        binding.setUser(user);
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

        if (user.isMeasurementKg()) {
            spWeight.setSelection(0);
        } else {
            spWeight.setSelection(1);
        }

        if (user.isMeasurementCm()) {
            spHeight.setSelection(0);
        } else {
            spHeight.setSelection(1);
        }

        spWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    binding.getUser().setWeightMeasurementKg();
                } else {
                    binding.getUser().setWeightMeasurementPound();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    binding.getUser().setHeightMeasurementCm();
                } else {
                    binding.getUser().setHeightMeasurementFeet();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @OnClick(R.id.rl_back)
    public void onBackPress() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPrefManager.setUser(this, binding.getUser());

    }

}



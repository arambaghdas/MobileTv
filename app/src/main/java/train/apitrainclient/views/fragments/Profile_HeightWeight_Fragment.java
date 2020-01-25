package train.apitrainclient.views.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import train.apitrainclient.R;
import com.pttrackershared.models.eventbus.User;
import train.apitrainclient.utils.AppUtils;

/**
 * RoutinesFragment shows list of routines assigned to current user.
 * Provides option to user to filter out workout based on routine and date.
 */

public class Profile_HeightWeight_Fragment extends BaseFragment {

    //region View References
    @BindView(R.id.til_weight_kg)
    TextInputLayout til_weight_kg;

    @BindView(R.id.til_height_cm)
    TextInputLayout til_height_cm;

    @BindView(R.id.et_height_cm)
    EditText etHeightCm;

    @BindView(R.id.til_inches_ft)
    TextInputLayout til_inches_ft;

    @BindView(R.id.et_inches_ft)
    EditText et_inches_ft;
//    @BindView(R.id.et_height_ft)
//    EditText etHeightFt;
//
//    @BindView(R.id.et_height_in)
//    EditText etHeightIn;
//
//    @BindView(R.id.et_weight_pound)
//    EditText etWeightPound;

    @BindView(R.id.et_weight_kg)
    EditText etWeightKg;

    @BindView(R.id.dummyText4)
    TextView dummyText4;

    @BindView(R.id.spinnerWeight)
    Spinner spinnerWeight;

    @BindView(R.id.spinnerHeight)
    Spinner spinnerHeight;

    static User user;
    int lastKg = -1;
    int lastLb = -1;
    int lastCm = -1;
    int lastFt = -1;
    int lastIn = -1;

    String weightSelected;
    String heightSelected;
    boolean kg = true;
    public static Profile_HeightWeight_Fragment newInstance(User userr) {
        user = userr;
        return new Profile_HeightWeight_Fragment();
    }


    public void initSpinners(){
        final String[] weightItems = new String[]{"kg", "lb"};
        final String[] heightItems = new String[]{"cm", "ft"};

        ArrayAdapter<String> weightAdapter = new ArrayAdapter<String>(getActivity(),
               R.layout.sprinner_heightweight_layout,weightItems);
        spinnerWeight.setAdapter(weightAdapter);
        ArrayAdapter<String> heightAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.sprinner_heightweight_layout,heightItems);
        spinnerHeight.setAdapter(heightAdapter);

        spinnerWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 weightSelected = weightItems[i];
                 if (weightSelected.equals("kg")){
                     if (!etWeightKg.getText().toString().isEmpty()){
                         if (lastKg == -1){
                             int kg = Integer.parseInt(etWeightKg.getText().toString());
                             lastKg = AppUtils.convertPoundToKgQ(kg);
                             etWeightKg.setText(lastKg + "");
                         }else {
                             etWeightKg.setText(lastKg + "");
                         }

                     }
                 }else {
                     if (!etWeightKg.getText().toString().isEmpty()){
                         if (lastLb == -1){
                             int pound = Integer.parseInt(etWeightKg.getText().toString());
                             lastLb = AppUtils.convertKgToPoundQ(pound);
                             etWeightKg.setText(lastLb + "");
                         }else {
                             etWeightKg.setText(lastLb + "");
                         }

                     }
                 }




            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 heightSelected = heightItems[i];
                til_inches_ft.setVisibility(View.VISIBLE);
                et_inches_ft.setVisibility(View.VISIBLE);
                 if (heightSelected.equals("ft")){
                     if (!etHeightCm.getText().toString().isEmpty()){
                         lastCm = Integer.parseInt(etHeightCm.getText().toString());
                     }
                     til_height_cm.setHint("foot");
                     til_inches_ft.setHint("inches");
                     if (lastFt == -1 && lastIn == -1){
                        if (!etHeightCm.getText().toString().isEmpty()){
                         int heightInCm = Integer.parseInt(etHeightCm.getText().toString());
                         String heightInF = AppUtils.convertCmToFootInch(heightInCm);
                         String fAndI[] = heightInF.split("/");
                         lastFt = Integer.parseInt(fAndI[0]);
                         lastIn = Integer.parseInt(fAndI[1]);
                         etHeightCm.setText(lastFt + "");
                         et_inches_ft.setText(lastIn + "");
                        }

                     }else {
                         til_height_cm.setHint("foot");
                         til_inches_ft.setHint("inches");
                         etHeightCm.setText(lastFt + "");
                         et_inches_ft.setText(lastIn + "");
                     }
                 }else {
                     til_height_cm.setHint("Height");
                     int inch = 0;
                     if (!et_inches_ft.getText().toString().isEmpty()){
                         inch = Integer.parseInt(et_inches_ft.getText().toString());
                     }
                     til_inches_ft.setVisibility(View.GONE);
                     et_inches_ft.setVisibility(View.GONE);
                     if (!etHeightCm.getText().toString().isEmpty() && !et_inches_ft.getText().toString().isEmpty()){
                         if (lastCm == -1) {
                             int foot = Integer.parseInt(etHeightCm.getText().toString());
                             lastCm = AppUtils.convertFeetInchesToCm(foot, inch);
                             etHeightCm.setText(lastCm + "");
                         }else {
                             etHeightCm.setText(lastCm + "");
                         }
                     }else if (!etHeightCm.getText().toString().isEmpty() && et_inches_ft.getText().toString().isEmpty()){
                            if (lastCm == -1){
                                lastCm = Integer.parseInt(etHeightCm.getText().toString());
                                etHeightCm.setText(lastCm + "");

                            }else {
                                etHeightCm.setText(lastCm + "");

                            }
                     }else {
                         til_height_cm.setHint("Height");
                     }
                 }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    @Override
    public void preStart(LayoutInflater inflater, @Nullable ViewGroup container) {
        super.preStart(inflater, container);
        rootView = inflater.inflate(R.layout.fragment_profile_heightweight, container, false);
        setHasOptionsMenu(true);
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initControllers() {
        super.initControllers();
    }

    @Override
    public void initListeners() {
        super.initListeners();
    }

    @Override
    public void postStart() {
        initSpinners();
        dummyText4.setText(getText(R.string.txt_questionaire_dummytext4));
        super.postStart();
    }

    @OnClick(R.id.btn_Next)
    void nextButtonClicked() {
        if (checkValidateion()) {
            if (weightSelected.equals("kg")){
             user.setWeight(etWeightKg.getText().toString());
            }else {
              int weight = Integer.parseInt(etWeightKg.getText().toString());
              user.setWeight(String.valueOf(AppUtils.convertPoundToKgQ(weight)));
            }

            if (heightSelected.equals("cm")){
                user.setHeight(etHeightCm.getText().toString());
            }else {
                int foot = Integer.parseInt(etHeightCm.getText().toString());
                int inch = Integer.parseInt(et_inches_ft.getText().toString());

                user.setHeight(AppUtils.convertFeetInchesToCm(foot,inch)+"");
            }



            Fragment fragment = Profile_FitnessGoal_Fragment.newInstance(user);
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.view_content, fragment);
            fragmentTransaction.addToBackStack("").commit();
        }
    }

//    @OnCheckedChanged(R.id.checkbox_height)
//    public void onHeightVisibilityChecked(CompoundButton compoundButton, boolean isChecked) {
//        if (isChecked) {
//            checkHeightset = 0;
//            etHeightCm.setText("0");
//        } else {
//            checkHeightset = 1;
//        }
//    }

    boolean checkValidateion() {
        boolean flag = true;

        if (etHeightCm.getText().toString().length() <= 0) {
            flag = false;
            til_height_cm.setErrorEnabled(true);
            til_height_cm.setError("Enter Height.");
        } else if (etWeightKg.getText().toString().length() <= 0) {
            flag = false;
            til_weight_kg.setErrorEnabled(true);
            til_weight_kg.setError("Enter weight.");
        }

        return flag;

    }

    @OnTextChanged(value = R.id.et_weight_kg, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void surnameChanged(CharSequence text) {
        til_weight_kg.setError(null);
        til_weight_kg.setErrorEnabled(false);
    }

    @OnTextChanged(value = R.id.et_height_cm, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void emailChanged(CharSequence text) {
        til_height_cm.setError(null);
        til_height_cm.setErrorEnabled(false);
    }


//    @OnCheckedChanged(R.id.checkbox_weight)
//    public void onWeightVisibilityChecked(CompoundButton compoundButton, boolean isChecked) {
//        if (isChecked) {
//            checkWeightset = 0;
//            etWeightKg.setText("0");
//        } else {
//            checkWeightset = 1;
//        }
//    }


}
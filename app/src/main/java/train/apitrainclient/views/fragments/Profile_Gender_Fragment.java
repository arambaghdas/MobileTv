package train.apitrainclient.views.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import train.apitrainclient.R;
import com.pttrackershared.models.eventbus.User;
import train.apitrainclient.services.DialogUtils;

/**
 * RoutinesFragment shows list of routines assigned to current user.
 * Provides option to user to filter out workout based on routine and date.
 */

public class Profile_Gender_Fragment extends BaseFragment {

    //region View References
    @BindView(R.id.til_dOb)
    TextInputLayout tilDOb;

    @BindView(R.id.dummyText4)
    TextView dummyText4;

    @BindView(R.id.genderMale)
    ImageView genderMale;

    @BindView(R.id.genderFemale)
    ImageView genderFemale;

    Calendar calendarSelectedDate;
    private DatePickerDialog datePickerDialog;
    int salectedDateField;

    @BindView(R.id.et_dOb)
    EditText etDob;


    static User user;
    public static String phoneNumber;
    int maleFemaleRadioSelected = 1;


    public static Profile_Gender_Fragment newInstance() {
        return new Profile_Gender_Fragment();
    }

    @Override
    public void preStart(LayoutInflater inflater, @Nullable ViewGroup container) {
        super.preStart(inflater, container);
        rootView = inflater.inflate(R.layout.fragment_profile_gender, container, false);
        setHasOptionsMenu(true);

//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void initData() {
        super.initData();
        onbackprssed();
    }

    @Override
    public void initControllers() {
        super.initControllers();
    }

    @Override
    public void initListeners() {
        super.initListeners();
        calendarSelectedDate = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendarSelectedDate = calendar;

                if (salectedDateField == R.id.et_dOb) {
                    if (!calendar.after(Calendar.getInstance())) {
                        setDob();
                    } else {
                        DialogUtils.ShowSnackbarAlert(context, "Don't select advance date");
                    }
                }
            }
        };

        datePickerDialog = new DatePickerDialog(context, dateSetListener, calendarSelectedDate
                .get(Calendar.YEAR), calendarSelectedDate.get(Calendar.MONTH),
                calendarSelectedDate.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void postStart() {
        dummyText4.setText(getText(R.string.txt_questionaire_dummytext4));
        super.postStart();
    }

    @OnClick(R.id.btn_Next)
    void nextButtonClicked() {
        if (checkValidateion()) {
            user.setDob(etDob.getText().toString());
            user.setGender(String.valueOf(maleFemaleRadioSelected));
            Fragment fragment = Profile_HeightWeight_Fragment.newInstance(user);
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.view_content, fragment);
            fragmentTransaction.addToBackStack("").commit();
        }
    }

    boolean checkValidateion() {
        boolean flag = true;

        if (etDob.getText().toString().length() <= 0) {
            flag = false;
            tilDOb.setErrorEnabled(true);
            tilDOb.setError("Enter Date of Birth.");        }

        return flag;

    }

    @OnTextChanged(value = R.id.et_dOb, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void emailChanged(CharSequence text) {
        tilDOb.setError(null);
        tilDOb.setErrorEnabled(false);
    }


    private void setDob() {
        Date dob = calendarSelectedDate.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String selectedDate = sdf.format(calendarSelectedDate.getTime());
        etDob.setText(selectedDate);
    }


    @OnClick(R.id.et_dOb)
    public void dateOfBirthClicked() {
        hideKeyboard();
        salectedDateField = R.id.et_dOb;
        datePickerDialog.show();
    }

    @OnClick(R.id.genderMale)
    public void maleselected() {
        genderMale.setImageResource(R.drawable.male);
        genderFemale.setImageResource(R.drawable.female);
        maleFemaleRadioSelected = 1;
    }

    @OnClick(R.id.genderFemale)
    public void femaleselected() {
        genderMale.setImageResource(R.drawable.male_inactive);
        genderFemale.setImageResource(R.drawable.female_active);
        maleFemaleRadioSelected = 2;
    }


    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    void onbackprssed() {

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    getActivity().getSupportFragmentManager().popBackStack();
                    return true;
                }
                return false;
            }
        });

    }


}
package train.apitrainclient.views.fragments;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import butterknife.ButterKnife;
import train.apitrainclient.R;
import train.apitrainclient.views.activities.SignUpActivity;

/**
 * RoutinesFragment shows list of routines assigned to current user.
 * Provides option to user to filter out workout based on routine and date.
 */

public class TutorialTwoFragment extends BaseFragment {

    Button createAccount;

    public static TutorialTwoFragment newInstance() {
        TutorialTwoFragment foodListFragments = new TutorialTwoFragment();
        return foodListFragments;
    }


    @Override
    public void preStart(LayoutInflater inflater, @Nullable ViewGroup container) {
        super.preStart(inflater, container);
        rootView = inflater.inflate(R.layout.tutorial_two_fragment, container, false);

        createAccount = rootView.findViewById(R.id.createaccount);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(getActivity(), SignUpActivity.class);
                startActivity(signUp);
            }
        });

    }

    @Override
    public void initViews() {
        ButterKnife.bind(getActivity());
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
        super.postStart();


    }

    private void initCollectionAdapters() {
        if (!isViewAttached()) {
            return;
        }
    }



    @Override
    public void onResume() {
        super.onResume();
    }
}
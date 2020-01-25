package train.apitrainclient.views.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import train.apitrainclient.R;
import train.apitrainclient.utils.AppUtils;

public class NoTrainingPlanFragment extends BaseFragment {

    TextView noPlanTxt;
    TextView webLink;
    TextView userToAnotherDevice;
    RelativeLayout mainLayout;
    public static String title;
    public static boolean noUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_no_plan, container, false);
        this.rootView = rootView;
        context = getActivity();
        setHasOptionsMenu(true);
        initData();
        initViews();

        return rootView;
    }

    public void initData() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu);

        MenuItem item = menu.findItem(R.id.action_add);
        item.setVisible(false);

        MenuItem add_client = menu.findItem(R.id.action_add_client);
        add_client.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void initViews() {
        noPlanTxt = (TextView) rootView.findViewById(R.id.no_plan_text);
        webLink = (TextView) rootView.findViewById(R.id.webLink);
        userToAnotherDevice = (TextView) rootView.findViewById(R.id.no_user);
        mainLayout = (RelativeLayout) rootView.findViewById(R.id.mainLayout);

//        AppUtils.checkForGenderView(getActivity(),mainLayout);

        if (noUser){
            userToAnotherDevice.setVisibility(View.VISIBLE);
            webLink.setVisibility(View.GONE);
            noPlanTxt.setVisibility(View.GONE);
        }


        noPlanTxt.setText("No " + title +" assigned");

        webLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.apitrain.com")));
            }
        });

    }

    public static NoTrainingPlanFragment newInstance(String comingFrom,boolean noUserActive) {
        NoTrainingPlanFragment fragment = new NoTrainingPlanFragment();
        title = comingFrom;
        noUser = noUserActive;
        return fragment;
    }

}

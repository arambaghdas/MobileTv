package train.apitrainclient.views.fragments;

import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindDrawable;

import butterknife.BindView;
import butterknife.OnClick;
import train.apitrainclient.R;


public class CircuitFinishedFragment extends BaseFragment {
    //region ButterKnife Resource References

    @BindView(R.id.tv_finished_ttl)
    TextView tv_finished;



    @BindDrawable(R.mipmap.ic_menu_white)
    Drawable drUpArrow;
    //endregion

    //region Other Variables


    //region Controllers Variables
    //endregion

    //region Overridden Methods from Activity Base Class
    //endregion

    //region Basic Methods needed for Activity flow

    /**
     * Performs all operations right after creation of activity
     */

    public static CircuitFinishedFragment newInstance() {
        CircuitFinishedFragment circuitFinishedFragmentt = new CircuitFinishedFragment();
        return circuitFinishedFragmentt;
    }

    @Override
    public void preStart(LayoutInflater inflater, @Nullable ViewGroup container) {
        super.preStart(inflater, container);
        rootView = inflater.inflate(R.layout.circuit_finished_layout, container, false);
        setHasOptionsMenu(true);
    }

    /**
     * Initializes independently all data(not dependent on view or controller) variables to be used in this activity.
     */
    @Override
    public void initData() {
        super.initData();
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
     * Implements listeners views actions and other items related to this activity.
     * Anonymous implementation of a listener is preferred as this helps in debugging
     */
    @Override
    public void initListeners() {
        super.initListeners();
    }

    /**
     * Performs activity specific business logic here after complete initialization of data, controllers and views.
     */
    @Override
    public void postStart() {
        super.postStart();

    }

    @OnClick(R.id.back_button)
    public void onBackButtonCLick (){
        getActivity().finish();

    }
    
    //endregion

    //region Callback Methods
    //endregion

}



package train.apitrainclient.views.fragments;

import android.graphics.Color;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import train.apitrainclient.R;
import train.apitrainclient.adapters.TrainingLogRecyclerAdapter;
import train.apitrainclient.listeners.OnGetTrainingLogsCompletionListener;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.TrainingLogsModel;
import com.pttrackershared.models.eventbus.User;

import train.apitrainclient.networks.retrofit.RestApiManager;
import train.apitrainclient.services.DialogUtils;
import train.apitrainclient.utils.AppUtils;
import train.apitrainclient.utils.SharedPrefManager;
import train.apitrainclient.utils.UserPrefManager;
import train.apitrainclient.views.activities.HomeActivity;

/**
 * @author Atif Ali
 * @since August 29, 2017 5:08 PM
 * TrainingPlanFragment shows list of routines assigned to current user.
 * Provides option to user to filter out workout based on routine and date.
 */

public class TrainingLogsFragment extends BaseFragment implements Paginate.Callbacks {

    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    @BindView(R.id.rv_training_logs)
    RecyclerView rvTrainingLogs;

    @BindView(R.id.days_trained_graph)
    RelativeLayout days_trained_graph;

    @BindView(R.id.swiperefresh)
    RelativeLayout mainLayout;

    public static TrainingLogRecyclerAdapter trainingLogRecyclerAdapter;
    private List<TrainingLog> workoutList;
    private List<TrainingLog> filteredWorkoutList;
    private String searchTerm;
    private int currentPage = 0;
    private boolean hasMore = true;
    private boolean loadingInProgress;
    private Paginate mPaginate;
    UserPrefManager userPrefManager;

    public static TrainingLogsFragment newInstance() {
        TrainingLogsFragment profileFragment = new TrainingLogsFragment();
        return profileFragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_withsearch, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);
        MenuItem item = menu.findItem(R.id.action_add);
        if (item != null)
            item.setVisible(false);
        MenuItem add_client=menu.findItem(R.id.action_add_client);
        if (item != null)
            add_client.setVisible(false);

        SearchView mSearchView = (SearchView) mSearch.getActionView();
        EditText searchEditText = mSearchView.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(Color.WHITE);
        searchEditText.setHintTextColor(Color.WHITE);
        mSearchView.setQueryHint("Search");


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchTerm = s.toString().trim();
                showTrainingLogs();
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void preStart(LayoutInflater inflater, @Nullable ViewGroup container) {
        super.preStart(inflater, container);
        rootView = inflater.inflate(R.layout.fragment_training_logs, container, false);
        setHasOptionsMenu(true);
        userPrefManager = new UserPrefManager(getActivity());
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void initData() {
        super.initData();
        workoutList = new ArrayList<>();
        filteredWorkoutList = new ArrayList<>();
        searchTerm = "";
    }

    @Override
    public void initControllers() {
        super.initControllers();
        String strUserAge = "0";

        if (HomeActivity.getInstance().user.getDobString() != null && !HomeActivity.getInstance().user.getDobString().isEmpty())
            strUserAge = HomeActivity.getInstance().user.getDobString().substring(0, 4);



        int userAge = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(strUserAge);
        trainingLogRecyclerAdapter = new TrainingLogRecyclerAdapter(context, filteredWorkoutList,userAge);
    }

    @Override
    public void initListeners() {
        super.initListeners();
    }

    @Override
    public void postStart() {
        super.postStart();

//        AppUtils.checkForGenderView(getActivity(),mainLayout);
        workoutList.clear();

        initCollectionAdapters();

        if (HomeActivity.networkConnection) {
            loadTrainingLogs(0);
        }else{
            TrainingLogsModel trainingLogsModel = SharedPrefManager.getTrainingLogs(context);
            workoutList = trainingLogsModel.trainingLogs;
            showTrainingLogs();
        }

    }

    private void initCollectionAdapters() {
        if (!isViewAttached()) {
            return;
        }
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvTrainingLogs.setLayoutManager(layoutManager);
        rvTrainingLogs.setAdapter(trainingLogRecyclerAdapter);
    }

    private void loadTrainingLogs(final int pageNum) {
        if (!isLoading()) {
            loadingInProgress = true;
            if (mPaginate == null)
            DialogUtils.ShowProgress(context, getString(R.string.progress_message_loading_training_logs));
            RestApiManager.getTrainingLogs(context, pageNum, new OnGetTrainingLogsCompletionListener() {
                @Override
                public void onSuccess(List<TrainingLog> trainingLogs2, int pageNum, int pageCount) {
                    if (getActivity() != null)
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                currentPage = pageNum;
                                for (int i = 0; i < trainingLogs2.size(); i++) {
                                    if (!workoutList.contains(trainingLogs2.get(i))){
                                        workoutList.add(trainingLogs2.get(i));
                                    }
                                }
//                                TrainingLogsFragment.this.workoutList.addAll(workoutList);
                                showTrainingLogs();
                                hasMore = currentPage < pageCount;
                                loadingInProgress = false;
                                if (mPaginate == null)
                                    DialogUtils.HideDialog();
                                if (hasMore && mPaginate == null) {
                                    mPaginate = Paginate.with(rvTrainingLogs, TrainingLogsFragment.this)
                                            .setLoadingTriggerThreshold(1)
                                            .addLoadingListItem(true)
                                            .build();
                                }
                            }
                        });
                }

                @Override
                public void onFailure(int errorCode, String errorMessage) {
                    DialogUtils.ShowSnackbarAlert(context, errorMessage);
                    loadingInProgress = false;
                }
            });
        }
    }

    private void filterTrainingLogs() {
        Collections.sort(workoutList, new Comparator<TrainingLog>() {
            public int compare(TrainingLog o1, TrainingLog o2) {
                if (o1.getDateCompleted() == null || o2.getDateCompleted() == null)
                    return 0;
                return o2.getDateCompleted().compareTo(o1.getDateCompleted());
            }
        });
        for (TrainingLog workout : workoutList) {
            if (workout.getName() != null)
                if (workout.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                    filteredWorkoutList.add(workout);
                } else {
                }
        }
    }

    private void addAllTrainingLogs() {
        Collections.sort(workoutList, new Comparator<TrainingLog>() {
            public int compare(TrainingLog o1, TrainingLog o2) {
                if (o1.getDateCompleted() == null || o2.getDateCompleted() == null)
                    return 0;
                return o2.getDateCompleted().compareTo(o1.getDateCompleted());
            }
        });
        filteredWorkoutList.addAll(workoutList);
    }

    private void showTrainingLogs() {
        if (!isViewAttached() || !userPrefManager.isLoggedIn()) {
            return;
        }

        filteredWorkoutList.clear();
        if (!searchTerm.isEmpty()) {
            filterTrainingLogs();
        } else {
            addAllTrainingLogs();
        }

        if (filteredWorkoutList.isEmpty()) {
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
            rvTrainingLogs.setVisibility(View.VISIBLE);
        }
        trainingLogRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore() {
        if (hasMore)
            loadTrainingLogs(++currentPage);
    }

    @Override
    public boolean isLoading() {
        return loadingInProgress;
    }

    @Override
    public boolean hasLoadedAllItems() {
        if (!hasMore) {
            if (mPaginate != null)
                mPaginate.unbind();
        }
        return !hasMore;
    }

    @Override
    public void onDestroy() {
        currentPage = 0;
        SharedPrefManager.setTrainingPlanPageCount(context, 0);
        SharedPrefManager.setTrainingPlanPage(context, 0);
        super.onDestroy();
    }
}
package train.apitrainclient.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import train.apitrainclient.R;
import train.apitrainclient.adapters.ExercisesNameRecyclerAdapter;
import train.apitrainclient.listeners.OnGetExerciseCompletionListener;

import com.pttrackershared.models.eventbus.Exercise;
import com.pttrackershared.models.eventbus.ExercisesModel;
import train.apitrainclient.networks.retrofit.RestApiManager;
import train.apitrainclient.services.DialogUtils;
import train.apitrainclient.utils.Constants;
import train.apitrainclient.utils.SharedPrefManager;
import train.apitrainclient.utils.UserPrefManager;
import train.apitrainclient.views.activities.BaseActivity;
import train.apitrainclient.views.activities.ExerciseDetailActivity;

/**
 * @author Atif Ali
 * @since August 29, 2017 5:08 PM
 * RoutinesFragment shows list of routines assigned to current user.
 * Provides option to user to filter out workout based on routine and date.
 */

public class ExercisesFragment extends BaseFragment {

    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    @BindView(R.id.rv_exercises)
    RecyclerView rvExercises;

    private ExercisesNameRecyclerAdapter exercisesNameRecyclerAdapter;
    private ArrayAdapter<String> sortingParameterAdapter;
    private List<Exercise> exerciseList;
    private List<Exercise> filteredExerciseList;
    private List<String> parameterList;
    private String searchTerm;
    public static Exercise selectedEx;
    public UserPrefManager userPrefManager;

    public static ExercisesFragment newInstance() {
        ExercisesFragment profileFragment = new ExercisesFragment();
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
        if (add_client != null)
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
                    showExercises();
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
        rootView = inflater.inflate(R.layout.fragment_exercises, container, false);
        setHasOptionsMenu(true);
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void initData() {
        super.initData();
        userPrefManager = new UserPrefManager(getActivity());
        exerciseList = new ArrayList<>();
        filteredExerciseList = new ArrayList<>();
        parameterList = new ArrayList<>();
        parameterList.add(getString(R.string.txt_name));
        parameterList.add(getString(R.string.txt_category));
        searchTerm = "";
    }

    @Override
    public void initControllers() {
        super.initControllers();
        exercisesNameRecyclerAdapter = new ExercisesNameRecyclerAdapter(context, filteredExerciseList);
        sortingParameterAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, parameterList);
    }

    @Override
    public void initListeners() {
        super.initListeners();
        exercisesNameRecyclerAdapter.setOnItemSelectedListener(new ExercisesNameRecyclerAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                moveToExerciseDetailScreen(filteredExerciseList.get(position));
            }
        });
    }

    @Override
    public void postStart() {
        super.postStart();
        initCollectionAdapters();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("graphInfoData", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("trainingPlan", "").matches("No")){
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            if (BaseActivity.networkConnection){
                loadExercises();
            }else {
                ExercisesModel exercisesModel = SharedPrefManager.getExercises(context);
                ExercisesFragment.this.exerciseList = exercisesModel.exercises;
                removeDuplicates();
                showExercises();
            }

        }
    }

    private void initCollectionAdapters() {
        if (!isViewAttached()) {
            return;
        }
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvExercises.setLayoutManager(layoutManager);
        rvExercises.setAdapter(exercisesNameRecyclerAdapter);
    }

    private void loadExercises() {
        DialogUtils.ShowProgress(context, getString(R.string.progress_messsage_loading_exercises));
        RestApiManager.getExercises(context, new OnGetExerciseCompletionListener() {
            @Override
            public void onSuccess(List<Exercise> exerciseList) {
                ExercisesFragment.this.exerciseList.clear();
                ExercisesFragment.this.exerciseList.addAll(exerciseList);
                removeDuplicates();
                showExercises();
                DialogUtils.HideDialog();
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {

            }
        });
    }

    private void filterExercises() {
        for (Exercise exercise : exerciseList) {
            if (exercise.getName() != null)
                if (exercise.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                    filteredExerciseList.add(exercise);
                }
        }
    }

    private void addAllTrainingLogs() {
        filteredExerciseList.addAll(exerciseList);
    }

    private void removeDuplicates(){
        for(int i = 0; i < exerciseList.size(); i++){
            for(int j = i; j < exerciseList.size(); j++){
                if(exerciseList.get(i).getExerciseId() == exerciseList.get(j).getExerciseId())
                    exerciseList.remove(j);
            }
        }
    }

    private void showExercises() {
        if (!isViewAttached() || !userPrefManager.isLoggedIn()) {
            return;
        }

        filteredExerciseList.clear();
        if (!searchTerm.isEmpty()) {
            filterExercises();
        } else {
            addAllTrainingLogs();
        }

        if (filteredExerciseList.isEmpty()) {
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
        }
        sortExercises();
        exercisesNameRecyclerAdapter.notifyDataSetChanged();
    }

    private void sortExercises() {
        switch (0) {
            case Constants.SORTING_PARAMETER_NAME:
                Collections.sort(filteredExerciseList, new Comparator<Exercise>() {
                    @Override
                    public int compare(Exercise exercise1, Exercise exercise2) {
                        return exercise1.getName().toLowerCase().compareTo(exercise2.getName().toLowerCase());
                    }
                });
                break;

            case Constants.SORTING_PARAMETER_CATEGORY:
                Collections.sort(filteredExerciseList, new Comparator<Exercise>() {
                    @Override
                    public int compare(Exercise exercise1, Exercise exercise2) {
                        if (exercise1.getCategory() != null && exercise2.getCategory() != null)
                            return exercise1.getCategory().toLowerCase().compareTo(exercise2.getCategory().toLowerCase());
                        else {
                            return exercise1.getName().toLowerCase().compareTo(exercise2.getName().toLowerCase());
                        }
                    }
                });
                break;
        }
    }

    private void moveToExerciseDetailScreen(Exercise exercise) {
        selectedEx = exercise;
        Intent intent = new Intent(context, ExerciseDetailActivity.class);
        intent.putExtra(ExerciseDetailActivity.SELECTED_EXERCISE, exercise);
        startActivity(intent);
    }
}
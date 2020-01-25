package train.apitrainclient.views.fragments;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import train.apitrainclient.R;
import train.apitrainclient.adapters.FoodListRecyclerAdapter;
import train.apitrainclient.adapters.FoodPlansRecyclerAdapter;
import com.pttrackershared.models.eventbus.Meals;
import com.pttrackershared.models.eventbus.MealsModel;

import train.apitrainclient.listeners.OnFoodListCompletionListener;
import train.apitrainclient.networks.retrofit.RestApiManager;
import train.apitrainclient.services.DialogUtils;
import train.apitrainclient.utils.AppUtils;
import train.apitrainclient.utils.NetworkUtils;
import train.apitrainclient.utils.SharedPrefManager;
import train.apitrainclient.utils.UserPrefManager;
import train.apitrainclient.views.activities.HomeActivity;

/**
 * RoutinesFragment shows list of routines assigned to current user.
 * Provides option to user to filter out workout based on routine and date.
 */

public class FoodListFragments extends BaseFragment {

    FoodListRecyclerAdapter foodListRecyclerAdapter;
    public static List<Meals> mealList;
    private List<Meals> filteredMealList;

    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.btn_monday)
    TextView btnMonday;
    @BindView(R.id.line_monday)
    View lineMonday;
    @BindView(R.id.btn_tuesday)
    TextView btnTuesday;
    @BindView(R.id.line_tuesday)
    View lineTuesday;
    @BindView(R.id.btn_wednesday)
    TextView btnWednesday;
    @BindView(R.id.line_wednesday)
    View lineWednesday;
    @BindView(R.id.btn_thuesday)
    TextView btnThuesday;
    @BindView(R.id.line_thuesday)
    View lineThuesday;
    @BindView(R.id.btn_friday)
    TextView btnFriday;
    @BindView(R.id.line_friday)
    View lineFriday;
    @BindView(R.id.btn_saturday)
    TextView btnSaturday;
    @BindView(R.id.line_saturday)
    View lineSaturday;
    @BindView(R.id.btn_sunday)
    TextView btnSunday;
    @BindView(R.id.line_sunday)
    View lineSunday;

    @BindView(R.id.daysLayout)
    LinearLayout daysLayout;
    @BindView(R.id.mealsRvLayout)
    LinearLayout mealsRvLayout;

    @BindView(R.id.foodlistRV)
    RecyclerView foodlistRV;
    @BindView(R.id.rv_clientlist)
    RecyclerView rv_clientlist;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    int selectedDay = 1;
    FoodListRecyclerAdapter foodListRecyclerAdapterMeal;
    FoodPlansRecyclerAdapter foodPlansRecyclerAdapter;
    Meals currentMealJsonModel;
    UserPrefManager userPrefManager;

    public static FoodListFragments newInstance() {
        FoodListFragments foodListFragments = new FoodListFragments();
        return foodListFragments;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void preStart(LayoutInflater inflater, @Nullable ViewGroup container) {
        super.preStart(inflater, container);
        rootView = inflater.inflate(R.layout.fragment_foodlist, container, false);
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

        mealList = new ArrayList<>();
        filteredMealList = new ArrayList<>();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        int count = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (count == 0) {
            selectedDay = 7;
        } else {
            selectedDay = count;
        }
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
        initCollectionAdapters();
//        AppUtils.checkForGenderView(getActivity(),mainLayout);
        if (NetworkUtils.IsNetworkAvailable(getActivity())){
        FoodList();
        } else {
            MealsModel mealsModel = SharedPrefManager.getMeals(getActivity());
            mealList = mealsModel.model;
            if (mealList.size() > 0) {
                foodListRecyclerAdapterMeal = new FoodListRecyclerAdapter(context, mealList);
                foodlistRV.setHasFixedSize(true);
                foodlistRV.setLayoutManager(new LinearLayoutManager(context));
                foodlistRV.setAdapter(foodListRecyclerAdapterMeal);
            } else {
                foodlistRV.setVisibility(View.GONE);
            }
            if (selectedDay == 1){
                btnMonday.performClick();
            }else if (selectedDay == 2){
                btnTuesday.performClick();
            }else if (selectedDay == 3){
                btnWednesday.performClick();
            }else if (selectedDay == 4){
                btnThuesday.performClick();
            }else if (selectedDay == 5){
                btnFriday.performClick();
            }else if (selectedDay == 6){
                btnSaturday.performClick();
            }else if (selectedDay == 7){
                btnSunday.performClick();
            }
        }
    }

    //region Business Logic Specific to this fragment
    private void initCollectionAdapters() {
        if (!isViewAttached()) {
            return;
        }
    }


    public void FoodList(){
        RestApiManager.getFoodList(context, new OnFoodListCompletionListener() {
                    @Override
                    public void onSuccess(List<Meals> userJsonModelList) {
                        mealList.addAll(userJsonModelList);
                        MealsModel mealsModel = new MealsModel();
                        mealsModel.model = (ArrayList<Meals>) userJsonModelList;
                        SharedPrefManager.setMeals(getActivity(),mealsModel);
                        if (mealList.size() > 0) {
                            foodListRecyclerAdapterMeal = new FoodListRecyclerAdapter(context, mealList);
                            foodlistRV.setHasFixedSize(true);
                            foodlistRV.setLayoutManager(new LinearLayoutManager(context));
                            foodlistRV.setAdapter(foodListRecyclerAdapterMeal);
                            MealsModel model = new MealsModel();
                            model.model = (ArrayList<Meals>)mealList;
                            if (getActivity() != null) {
                                SharedPrefManager.removeLogsForSync(getActivity());
                                SharedPrefManager.addFoods(model,getActivity());
                            }
                        } else {
                            foodlistRV.setVisibility(View.GONE);
                        }

                        if (selectedDay == 1){
                            btnMonday.performClick();
                        }else if (selectedDay == 2){
                            btnTuesday.performClick();
                        }else if (selectedDay == 3){
                            btnWednesday.performClick();
                        }else if (selectedDay == 4){
                            btnThuesday.performClick();
                        }else if (selectedDay == 5){
                            btnFriday.performClick();
                        }else if (selectedDay == 6){
                            btnSaturday.performClick();
                        }else if (selectedDay == 7){
                            btnSunday.performClick();
                        }

                        DialogUtils.HideDialog();
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        String errorCodes = errorMessage;
                    }
                });
    }

   private void showMealList() {
        if (!isViewAttached() || !userPrefManager.isLoggedIn()) {
            return;
        }

        try {
            filteredMealList.clear();
            filterMeal();
            if (filteredMealList.isEmpty()) {
                tvNoData.setVisibility(View.VISIBLE);
            } else {
                tvNoData.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterMeal() {
        for (Meals meal : mealList) {
            if (meal.getDay() == selectedDay) {
                filteredMealList.add(meal);
            }
        }

        Log.d("checkNitin", "filteredMealList:" + filteredMealList);
        foodPlansRecyclerAdapter = new FoodPlansRecyclerAdapter(context,filteredMealList);
        rv_clientlist.setHasFixedSize(true);
        rv_clientlist.setLayoutManager(new LinearLayoutManager(context));
        rv_clientlist.setAdapter(foodPlansRecyclerAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("change", "msss");
    }

    @OnClick(R.id.btn_monday)
    void clickedBtnMonday() {
        selectedDay = 1;
        for (int i = 0; i < daysLayout.getChildCount(); i++) {
            View layout = daysLayout.getChildAt(i);
            if (layout instanceof LinearLayout){
                for (int j = 0; j < ((LinearLayout) layout).getChildCount(); j++) {
                    View line = ((LinearLayout) layout).getChildAt(j);
                    if (line instanceof TextView){
                        ((TextView) line).setTextColor(context.getResources().getColor(R.color.light_grey));
                    }else {
                        line.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                }

            }
        }
        lineMonday.setBackgroundColor(context.getResources().getColor(R.color.green_grapgh));
        btnMonday.setTextColor(context.getResources().getColor(R.color.white));
        showMealList();

    }

    @OnClick(R.id.btn_tuesday)
    void clickedBtnTuesday() {
        selectedDay = 2;
        for (int i = 0; i < daysLayout.getChildCount(); i++) {
            View layout = daysLayout.getChildAt(i);
            if (layout instanceof LinearLayout){
                for (int j = 0; j < ((LinearLayout) layout).getChildCount(); j++) {
                    View line = ((LinearLayout) layout).getChildAt(j);
                    if (line instanceof TextView){
                        ((TextView) line).setTextColor(context.getResources().getColor(R.color.light_grey));
                    }else {
                        line.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                }

            }
        }
        lineTuesday.setBackgroundColor(context.getResources().getColor(R.color.green_grapgh));
        btnTuesday.setTextColor(context.getResources().getColor(R.color.white));
        showMealList();
    }

    @OnClick(R.id.btn_wednesday)
    void clickedBtnWednesday() {
        selectedDay = 3;
        for (int i = 0; i < daysLayout.getChildCount(); i++) {
            View layout = daysLayout.getChildAt(i);
            if (layout instanceof LinearLayout){
                for (int j = 0; j < ((LinearLayout) layout).getChildCount(); j++) {
                    View line = ((LinearLayout) layout).getChildAt(j);
                    if (line instanceof TextView){
                        ((TextView) line).setTextColor(context.getResources().getColor(R.color.light_grey));
                    }else {
                        line.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                }

            }
        }
        lineWednesday.setBackgroundColor(context.getResources().getColor(R.color.green_grapgh));
        btnWednesday.setTextColor(context.getResources().getColor(R.color.white));
        showMealList();
    }

    @OnClick(R.id.btn_thuesday)
    void clickedBtnThuesday() {
        selectedDay = 4;
        for (int i = 0; i < daysLayout.getChildCount(); i++) {
            View layout = daysLayout.getChildAt(i);
            if (layout instanceof LinearLayout){
                for (int j = 0; j < ((LinearLayout) layout).getChildCount(); j++) {
                    View line = ((LinearLayout) layout).getChildAt(j);
                    if (line instanceof TextView){
                        ((TextView) line).setTextColor(context.getResources().getColor(R.color.light_grey));
                    }else {
                        line.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                }

            }
        }
        lineThuesday.setBackgroundColor(context.getResources().getColor(R.color.green_grapgh));
        btnThuesday.setTextColor(context.getResources().getColor(R.color.white));
        showMealList();
    }

    @OnClick(R.id.btn_friday)
    void clickedBtnFriday() {
        selectedDay = 5;
        for (int i = 0; i < daysLayout.getChildCount(); i++) {
            View layout = daysLayout.getChildAt(i);
            if (layout instanceof LinearLayout){
                for (int j = 0; j < ((LinearLayout) layout).getChildCount(); j++) {
                    View line = ((LinearLayout) layout).getChildAt(j);
                    if (line instanceof TextView){
                        ((TextView) line).setTextColor(context.getResources().getColor(R.color.light_grey));
                    }else {
                        line.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                }

            }
        }
        lineFriday.setBackgroundColor(context.getResources().getColor(R.color.green_grapgh));
        btnFriday.setTextColor(context.getResources().getColor(R.color.white));
        showMealList();
    }

    @OnClick(R.id.btn_saturday)
    void clickedBtnSaturday() {
        selectedDay = 6;
        for (int i = 0; i < daysLayout.getChildCount(); i++) {
            View layout = daysLayout.getChildAt(i);
            if (layout instanceof LinearLayout){
                for (int j = 0; j < ((LinearLayout) layout).getChildCount(); j++) {
                    View line = ((LinearLayout) layout).getChildAt(j);
                    if (line instanceof TextView){
                        ((TextView) line).setTextColor(context.getResources().getColor(R.color.light_grey));
                    }else {
                        line.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                }

            }
        }
        lineSaturday.setBackgroundColor(context.getResources().getColor(R.color.green_grapgh));
        btnSaturday.setTextColor(context.getResources().getColor(R.color.white));
        showMealList();
    }

    @OnClick(R.id.btn_sunday)
    void clickedBtnSunday() {
        selectedDay = 7;
        for (int i = 0; i < daysLayout.getChildCount(); i++) {
            View layout = daysLayout.getChildAt(i);
            if (layout instanceof LinearLayout){
                for (int j = 0; j < ((LinearLayout) layout).getChildCount(); j++) {
                    View line = ((LinearLayout) layout).getChildAt(j);
                    if (line instanceof TextView){
                        ((TextView) line).setTextColor(context.getResources().getColor(R.color.light_grey));
                    }else {
                        line.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }
                }

            }
        }
        lineSunday.setBackgroundColor(context.getResources().getColor(R.color.green_grapgh));
        btnSunday.setTextColor(context.getResources().getColor(R.color.white));
        showMealList();
    }

    public void showMealDetails(){
        mealsRvLayout.setVisibility(View.GONE);
        showMealList();
    }
}
package train.apitrainclient.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import train.apitrainclient.R;
import train.apitrainclient.adapters.HelpFeedbackRecyclerAdapter;
import train.apitrainclient.listeners.OnGetFeqsListCompletionListener;
import com.pttrackershared.models.eventbus.FequsModel;
import com.pttrackershared.models.eventbus.User;
import train.apitrainclient.networks.retrofit.RestApiManager;
import train.apitrainclient.utils.AppUtils;
import train.apitrainclient.utils.SharedPrefManager;

/**
 * RoutinesFragment shows list of routines assigned to current user.
 * Provides option to user to filter out workout based on routine and date.
 */

public class HelpFeedbackFragment extends BaseFragment {

    @BindView(R.id.helpRV)
    RecyclerView helpRV;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    private String searchTerm;

    HelpFeedbackRecyclerAdapter helpFeedbackRecyclerAdapter;
    List<FequsModel> feqsList=new ArrayList<>();

    public static HelpFeedbackFragment newInstance() {
        HelpFeedbackFragment foodListFragments = new HelpFeedbackFragment();
        return foodListFragments;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_help_feedback, menu);
        MenuItem mSearch = menu.findItem(R.id.action_search);
        MenuItem mail = menu.findItem(R.id.send_mail);
        
        mail.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                openGmail();
                return false;
            }
        });
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        ImageView icon = mSearchView.findViewById(R.id.search_button);
        icon.setColorFilter(getActivity().getResources().getColor(R.color.background_blue));
        EditText searchEditText = mSearchView.findViewById(R.id.search_src_text);
        ImageView searchClose = (ImageView) mSearchView.findViewById(R.id.search_close_btn);
        searchClose.setColorFilter(getResources().getColor(R.color.background_blue), PorterDuff.Mode.SRC_ATOP);
        searchEditText.setTextColor(Color.BLACK);
        searchEditText.setHintTextColor(Color.BLACK);
        mSearchView.setQueryHint("Search");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchTerm = s.toString().trim();
                if (s.length() > 3) {
                    ArrayList<FequsModel> fequsModelsTemp = new ArrayList<>();
                    for (int i = 0; i < feqsList.size(); i++) {
                        FequsModel fequsModel = feqsList.get(i);
                        if (fequsModel.getQuestion().toLowerCase().contains(searchTerm.toLowerCase())) {
                            fequsModelsTemp.add(fequsModel);
                        }
                    }
                    helpFeedbackRecyclerAdapter = new HelpFeedbackRecyclerAdapter(getActivity(), fequsModelsTemp);
                    helpRV.setHasFixedSize(true);
                    helpRV.setLayoutManager(new LinearLayoutManager(getActivity()));
                    helpRV.setAdapter(helpFeedbackRecyclerAdapter);


                } else {
                        helpFeedbackRecyclerAdapter = new HelpFeedbackRecyclerAdapter(getActivity(), feqsList);
                        helpRV.setHasFixedSize(true);
                        helpRV.setLayoutManager(new LinearLayoutManager(getActivity()));
                        helpRV.setAdapter(helpFeedbackRecyclerAdapter);
                }
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
        rootView = inflater.inflate(R.layout.activity_help_feedback, container, false);
        setHasOptionsMenu(true);
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
//        AppUtils.checkForGenderView(getActivity(),mainLayout);
        loadFeqsLIst();

        if (feqsList.size() < 1){
            feqsList.add(createTopics("What are the weekly achievements? ","The weekly achievements will show you for the current week the total calories burnt and also the total number of workouts succesfully completed."));
            feqsList.add(createTopics("If I dont finish a workout, will it appear in my weekly achievements? ","Only completed workouts appear in the weekly achievements. If you exit the workout before completing it, this will not be recorded as completed and therefore not appear in your weekly achievements"));
            feqsList.add(createTopics("How can I get an APITRAIN Smart Watch?","Go to www.apitrain.com, log into your account and look for the online shop."));
            feqsList.add(createTopics("Can I use my IWatch or Android Wear instead of APITRAIN Watch?","Not for now. APITRAIN Training AID will be available for IWatch and Android Wear in the  near future. Check www.apitrain.com for further updates"));
            feqsList.add(createTopics("I dont have any training log. How do I get training logs to appear in the screen?","Training logs are automatically generated after completing a workout through the APITRAIN Smart Watch. These logs will show you the total duration of your workout, the amount of calories burnt and the average effort zone you worked in."));
            feqsList.add(createTopics("I dont have a training plan. What do I do?","If you are a client of a personal trainer you will need to contact your PT so they can assign you a training plan through their APITRAIN membership area. \n" +
                    "If you are a PT or an Individual, visit www.apitrain.com and log into your membership area. Then navigate to the Training Plans page and assign you your preferred training plan."));
            feqsList.add(createTopics("I have assigned a new training plan but I still see the old one.","Make sure you are connected to the internet. Log out from the app and log back in. You should now see the new training plan."));
            feqsList.add(createTopics("How do I create a new  training plan?","Only PTs and Individuals can create training plans through the website. \n" +
                    "To create a new training plan please visit www.apitrain.com and log into your membership area."));
            feqsList.add(createTopics("I cannot see the exercise video","To see exercise videos you need to be connected to the internet. Please connect to the internet and try again."));
            feqsList.add(createTopics("I dont have a food plan. What do I do?","If you are a client of a personal trainer you will need to contact your PT so they can assign you a food plan through their APITRAIN membership area. \n" +
                    "If you are a PT or an Individual, visit www.apitrain.com and log into your membership area. Then navigate to the Food Plans page and assign you your preferred food plan."));
            feqsList.add(createTopics("I have assigned a new food plan but I still see the old one.","Make sure you are connected to the internet. Log out from the app and log back in. You should now see the new food plan."));
            feqsList.add(createTopics("How do I create a new  food plan?","Only PTs and Individuals can create food plans through the website. \n" +
                    "To create a new food plan please visit www.apitrain.com and log into your membership area."));
            feqsList.add(createTopics("What is the APITRAIN Smart Watch PIN Code?","This is the code you need to use along with your telephone number to log into your APITRAIN Smart Watch. If you still don't have one, go to www.apitrain.com, log into your account and look for the online shop."));

            helpFeedbackRecyclerAdapter = new HelpFeedbackRecyclerAdapter(getActivity(), feqsList);
            helpRV.setHasFixedSize(true);
            helpRV.setLayoutManager(new LinearLayoutManager(getActivity()));
            helpRV.setAdapter(helpFeedbackRecyclerAdapter);

        }

    }

    private void initCollectionAdapters() {
        if (!isViewAttached()) {
            return;
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        Log.d("change", "msss");
    }

    private void loadFeqsLIst() {
        RestApiManager.getFeqsList(context, User.getSelectedLanguageCodeChange(context), new OnGetFeqsListCompletionListener() {
            @Override
            public void onSuccess(List<FequsModel> fequsList) {
                feqsList.clear();
                feqsList.addAll(fequsList);
                helpFeedbackRecyclerAdapter = new HelpFeedbackRecyclerAdapter(getActivity(), feqsList);
                helpRV.setHasFixedSize(true);
                helpRV.setLayoutManager(new LinearLayoutManager(getActivity()));
                helpRV.setAdapter(helpFeedbackRecyclerAdapter);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {

            }
        });
    }

    void openGmail() {
        User user = SharedPrefManager.getUser(context);

        String version = "";
        try {
            PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
//       int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE;

        String str = "App Information :\n" +
                "App Version Code : " + version + "\n" +
                "OS Version : " + versionRelease + "\n" +
                "Model : " + model + "\n" +
                "Screen : " + getScreenResolution(getActivity()) + "\n" +
                "Username : " + user.getClientemail() + "\n" +
                "Session : " + user.getPackage_item();


        Intent intent = new Intent(Intent.ACTION_SEND);
        String[] recipients = {"android@apitrain.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Android Feedback...");
        intent.putExtra(Intent.EXTRA_TEXT, "" + str);
        intent.setType("text/html");
        intent.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(intent, "Send mail"));
    }

    private static String getScreenResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        return "{" + width + "," + height + "}";
    }

    public FequsModel createTopics(String question,String answer){
        FequsModel fequsModel = new FequsModel();
        fequsModel.setQuestion(question);
        fequsModel.setAnswer(answer);
        return fequsModel;
    }
}
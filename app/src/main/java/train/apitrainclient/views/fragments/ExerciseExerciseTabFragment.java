package train.apitrainclient.views.fragments;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.youtube.player.YouTubePlayer;
import com.pttrackershared.models.eventbus.Exercise;
import com.pttrackershared.views.widgets.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;
import train.apitrainclient.R;
import com.pttrackershared.models.eventbus.User;
import train.apitrainclient.utils.Constants;
import train.apitrainclient.utils.SharedPrefManager;
import train.apitrainclient.utils.ValidatorUtils;

/**
 * Template activity should used whenever creating a new activity.
 * This class contains a template structure for an activity
 */

public class ExerciseExerciseTabFragment extends BaseFragment {

    //region ButterKnife Resource References
    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_category)
    TextView tvCategory;

    @BindView(R.id.iv_image_exercise)
    ImageView ivExerciseImage;

    @BindView(R.id.tv_description)
    TextView tv_description;

    @BindColor(R.color.white)
    int colorWhite;

    @BindDrawable(R.mipmap.ic_menu_white)
    Drawable drUpArrow;

    @BindView(R.id.backButton)
    ImageView backButton;

    @BindView(R.id.ytPlayerView)
    YouTubePlayerView ytPlayerView;
    //endregion

    //region Other Variables
    public static final String SELECTED_EXERCISE = "selected_exercise";
    public static boolean isFullScreen;
    public static YouTubePlayer youTubePlayer;
    private Exercise selectedExercise;
    private String youtubeVideoId;
    //endregion

    //region Controllers Variables
    //endregion

    //region Overridden Methods from Activity Base Class
    //endregion

    //region Basic Methods needed for Activity flow

    /**
     * Performs all operations right after creation of activity
     */

    public static ExerciseExerciseTabFragment newInstance() {
        ExerciseExerciseTabFragment exerciseTabFragment = new ExerciseExerciseTabFragment();
        return exerciseTabFragment;
    }

    @Override
    public void preStart(LayoutInflater inflater, @Nullable ViewGroup container) {
        super.preStart(inflater, container);
        rootView = inflater.inflate(R.layout.exercise_detail_fragment, container, false);
        setHasOptionsMenu(true);
    }

    /**
     * Initializes independently all data(not dependent on view or controller) variables to be used in this activity.
     */
    @Override
    public void initData() {
        super.initData();
        selectedExercise = ExercisesFragment.selectedEx;
        selectedExercise.setImageName(ExercisesFragment.selectedEx.getImageName());

        if (!ValidatorUtils.IsNullOrEmpty(selectedExercise.getYoutubeLink()) && selectedExercise.getYoutubeLink().matches(".*(youtube|youtu.be).*")) {
            Uri uri = Uri.parse(selectedExercise.getYoutubeLink());
            youtubeVideoId = getVideoid(uri.toString());
        }
        ytPlayerView.isFullScreen = false;
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
        ytPlayerView.setActivity(getActivity());
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
        showData();
    }

    private void addYoutubeVideo() {
        ytPlayerView.addYoutubeVideo(youtubeVideoId);
    }

    private void showData() {
        if (selectedExercise != null){
            tvName.setText(selectedExercise.getName());
            tvCategory.setText(selectedExercise.getCategory_name());
            if( selectedExercise.getExercise_description()!=null) {
                tv_description.setText(selectedExercise.getExercise_description());
            }else {
                tv_description.setText("Description not available");
            }

          if (selectedExercise.getImageLink() != null && !selectedExercise.getImageLink().equalsIgnoreCase("")) {
                String imageName = selectedExercise.getImageName();
                User user = SharedPrefManager.getUser(context);
                if (user.getGender().equalsIgnoreCase("2")) {
                    Picasso.with(context).load(Constants.IMAGE_URL_FEMALE + imageName).
                            error(R.drawable.error_image).into(ivExerciseImage);
                } else {
                    Picasso.with(context).load(Constants.IMAGE_URL_MALE +
                            imageName).
                            error(R.drawable.error_image).into(ivExerciseImage);
                }
            } else {
                ivExerciseImage.setImageResource(R.drawable.error_image);
            }
            addYoutubeVideo();
        }

    }

    public String getVideoid(String url){

        String[] firstSplit = url.split("//");

        String[] secondSplit = firstSplit[1].split("/");

        return secondSplit[1];

    }


    @OnClick(R.id.backButton)
    public void back(){
        getActivity().finish();
    }

}


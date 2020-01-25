package com.pttrackershared.views.widgets;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.pttrackershared.R;
import com.pttrackershared.utils.Constants;


/**
 * TODO: document your custom view class.
 */
public class YouTubePlayerView extends RelativeLayout {

    Context context;
    FragmentActivity activity;
    AppCompatActivity activityTraining;
    public static YouTubePlayer youTubePlayer;
    public static boolean isFullScreen;

    public YouTubePlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public YouTubePlayerView(Context context, Context context1) {
        super(context);
        this.context = context1;
        init();
    }

    public YouTubePlayerView(Context context, AttributeSet attrs, int defStyleAttr, Context context1) {
        super(context, attrs, defStyleAttr);
        this.context = context1;
        init();
    }

    public YouTubePlayerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, Context context1) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context1;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.youtube_custom_view, this, true);
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public void setActivityTraining(AppCompatActivity activity) {
        this.activityTraining = activity;
    }

    public void addYoutubeVideo(final String youtubeVideoId) {
        final YouTubePlayerSupportFragment youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fl_youtube_video, youTubePlayerSupportFragment).commit();


        youTubePlayerSupportFragment.initialize(Constants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b) {
                youTubePlayer = player;
                if (youtubeVideoId != null) {
                    player.cueVideo(youtubeVideoId);
                }
                player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                    @Override
                    public void onFullscreen(boolean b) {
                        isFullScreen = b;
                    }
                });
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.getSupportFragmentManager().beginTransaction().remove(youTubePlayerSupportFragment).commit();
                    }
                });
            }
        });
    }

    public void addYoutubeVideoTraining(final String youtubeVideoId) {
        final YouTubePlayerSupportFragment youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
        activityTraining.getSupportFragmentManager().beginTransaction().replace(R.id.fl_youtube_video, youTubePlayerSupportFragment).commit();


        youTubePlayerSupportFragment.initialize(Constants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b) {
                youTubePlayer = player;
                if (youtubeVideoId != null) {
                    player.cueVideo(youtubeVideoId);
                }
                player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                    @Override
                    public void onFullscreen(boolean b) {
                        isFullScreen = b;
                    }
                });
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                activityTraining.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activityTraining.getSupportFragmentManager().beginTransaction().remove(youTubePlayerSupportFragment).commit();
                    }
                });
            }
        });
    }

}

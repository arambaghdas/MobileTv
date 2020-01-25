package com.pttracker.trainingaid.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.pttracker.trainingaid.Class.TrainingAid;
import com.pttracker.trainingaid.R;
import com.pttracker.trainingaid.utils.SaveUserPreferences;
import com.pttrackershared.models.eventbus.Exercise;
import com.pttrackershared.models.eventbus.User;
import com.pttrackershared.utils.Constants;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ExerciseExitFragment extends BaseFragment {

    ImageView ivExerciseImage;
    RelativeLayout rlExit;

    private boolean isViewAttached = false;
    private Unbinder unbinder;

    private View rootView;
    private Context context;
    private int exerciseIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rect_fragment_exercise_exit, container, false);
        this.rootView = rootView;
        context = getActivity();

        initViews();
        setIvExerciseImage();

        rlExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you want to exit the app?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TrainingAid.getInstance().getActivity().finish();
                                getActivity().finish();
                                quit();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Nothing happens
                            }
                        });
                builder.create();
                builder.show();
            }
        });

        return  rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
            setIvExerciseImage();
    }

    public void initViews() {
        unbinder = ButterKnife.bind(this, rootView);

        ivExerciseImage = (ImageView) rootView.findViewById(R.id.iv_exercise_image);
        rlExit = (RelativeLayout) rootView.findViewById(R.id.rl_exit);

        isViewAttached = true;
    }

    private void setIvExerciseImage() {

        Exercise exercise = ExerciseFragment.exerciseList.get(ExerciseFragment.exerciseIndex);

        if (!exercise.getImageLink().equalsIgnoreCase("")) {
//            Picasso.with(context).load(selectedExercise.getImageLink()).
//                    error(R.drawable.error_image).into(ivExerciseImage);
            String imageName = exercise.getImageName();
            User user = SaveUserPreferences.getUser(context);
            if (user.getGender().equalsIgnoreCase("2")) {
                Picasso.with(context).load(Constants.IMAGE_URL_FEMALE +
                        imageName).
                        error(R.drawable.error_image).into(ivExerciseImage);
            } else {
                Picasso.with(context).load(Constants.IMAGE_URL_MALE +
                        imageName).
                        error(R.drawable.error_image).into(ivExerciseImage);
            }
        } else {
            ivExerciseImage.setImageResource(R.drawable.error_image);
        }
    }

    public static ExerciseExitFragment newInstance(){
        ExerciseExitFragment fragment = new ExerciseExitFragment();
        return  fragment;
    }

    public void quit() {
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        System.exit(1);
    }
}

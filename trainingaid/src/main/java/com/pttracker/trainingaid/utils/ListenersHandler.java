package com.pttracker.trainingaid.utils;

import com.pttracker.trainingaid.networks.MealJsonModel;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.TrainingPlanJsonModel;

import java.util.List;

public class ListenersHandler {


    public static interface OnUpdateTrainingLogCompletionListener {
        void onSuccess(TrainingLog trainingLog);

        void onFailure(int errorCode, String errorMessage);
    }

    public static interface OnGetTrainingPlansCompletionListener {
        void onSuccess(List<TrainingPlanJsonModel> trainingPlanJsonModelList);

        void onFailure(int errorCode, String errorMessage);
    }

    public static interface OnFoodListCompletionListener {
        void onSuccess(List<MealJsonModel> userJsonModelList);

        void onFailure(int errorCode, String errorMessage);
    }

    public static interface OnLoginViaEmailCompletionListener {
        void onSuccess();

        void onFailure(int errorCode, String errorMessage);
    }

}

package train.apitrainclient.listeners;

import com.pttrackershared.models.eventbus.TrainingPlanJsonModel;

import java.util.List;

public interface OnGetTrainingPlanListCompletionListener {

    void onSuccess(List<TrainingPlanJsonModel> trainingPlanViewJsonModel);

    void onFailure(int errorCode, String errorMessage);
}

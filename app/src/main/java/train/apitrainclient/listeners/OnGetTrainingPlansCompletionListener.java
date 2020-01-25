package train.apitrainclient.listeners;
import com.pttrackershared.models.eventbus.TrainingPlanJsonModel;

import java.util.List;

public interface OnGetTrainingPlansCompletionListener {
    void onSuccess(List<TrainingPlanJsonModel> trainingPlanList);

    void onFailure(int errorCode, String errorMessage);
}

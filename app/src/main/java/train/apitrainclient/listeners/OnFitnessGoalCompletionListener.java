package train.apitrainclient.listeners;

import java.util.List;

import com.pttrackershared.models.eventbus.FitnessGoalModel;

public interface OnFitnessGoalCompletionListener {
    void onSuccess(List<FitnessGoalModel> fitnessGoalModelList);

    void onFailure(int errorCode, String errorMessage);
}

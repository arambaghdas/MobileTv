package train.apitrainclient.listeners;

import com.pttrackershared.models.eventbus.TrainingPlanView;

public interface OnGetTrainingPlanListener {
        void onSuccess(TrainingPlanView trainingPlanViewJsonModelList);

        void onFailure(int errorCode, String errorMessage);

}

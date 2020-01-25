package train.apitrainclient.listeners;


import com.pttrackershared.models.eventbus.Exercise;

import java.util.List;

public interface OnGetExerciseCompletionListener {
    void onSuccess(List<Exercise> exerciseList);

    void onFailure(int errorCode, String errorMessage);
}

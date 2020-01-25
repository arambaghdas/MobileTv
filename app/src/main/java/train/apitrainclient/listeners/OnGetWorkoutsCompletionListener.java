package train.apitrainclient.listeners;

import com.pttrackershared.models.eventbus.Workout;

import java.util.List;

public interface OnGetWorkoutsCompletionListener {
    void onSuccess(List<Workout> workoutJsonModelList);

    void onFailure(int errorCode, String errorMessage);
}

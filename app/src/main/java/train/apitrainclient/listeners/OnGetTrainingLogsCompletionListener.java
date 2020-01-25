package train.apitrainclient.listeners;

import java.util.List;

import com.pttrackershared.models.eventbus.TrainingLog;

public interface OnGetTrainingLogsCompletionListener {
    void onSuccess(List<TrainingLog> trainingLogs, int pageNum, int pageCount);

    void onFailure(int errorCode, String errorMessage);
}

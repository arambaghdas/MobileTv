package train.apitrainclient.listeners;

import java.util.List;

import com.pttrackershared.models.eventbus.Meals;

public interface OnFoodListCompletionListener {
    void onSuccess(List<Meals> userJsonModelList);

    void onFailure(int errorCode, String errorMessage);
}

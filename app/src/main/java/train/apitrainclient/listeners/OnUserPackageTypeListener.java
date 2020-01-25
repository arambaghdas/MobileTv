package train.apitrainclient.listeners;

import java.util.List;

import com.pttrackershared.models.eventbus.PackageTypeModel;

public interface OnUserPackageTypeListener {
    void onSuccess(List<PackageTypeModel> packageTypeList);

    void onFailure(int errorCode, String errorMessage);
}

package train.apitrainclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.pttrackershared.utils.SaveUserPreferences;

public class UserPrefManager {

    public Context context;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public UserPrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getAccessToken() {
        return sharedPreferences.getString("access_token", "");
    }

    public void saveAccessToken(String weightUnit) {
        editor.putString("access_token", weightUnit);
        editor.commit();
    }

    public void clearPrefs(Context context) {
        setLoggedIn(false);
        editor.putBoolean("is_logged_in", false);
        editor.putString("access_token", "");
        editor.putBoolean("is_login_updated", true);
        editor.putLong("last_synced", -1);
        editor.commit();
    }

    public void setLogoutUpdated(boolean isLogoutUpdated) {
        editor.putBoolean("is_logout_updated", isLogoutUpdated);
        editor.commit();
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean("is_logged_in", isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("is_logged_in", false) && SaveUserPreferences.getUser(context) != null && SharedPrefManager.getUser(context).getClientemail() != null;
    }

}

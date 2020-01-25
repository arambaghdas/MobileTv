package train.apitrainclient.plugins;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import train.apitrainclient.R;

public class CustomProgressDialog {

    public static CustomProgressDialog customProgress = null;
    private Dialog mDialog;
    TextView progressText;

    public static CustomProgressDialog getInstance() {
        if (customProgress == null) {
            customProgress = new CustomProgressDialog();
        }
        return customProgress;
    }

    public void showProgress(Context context, String message, boolean cancelable) {
        mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.progress_bar_dialog);
        progressText = (TextView) mDialog.findViewById(R.id.progress_text);
        progressText.setText("" + message);
        progressText.setVisibility(View.VISIBLE);
        mDialog.setCancelable(cancelable);
        mDialog.setCanceledOnTouchOutside(cancelable);
        mDialog.show();
        mDialog.getWindow().setLayout(235,120);
    }

    public void setMessage(String message){
        if (mDialog != null){
            progressText.setText(message);
        }
    }

    public void hideProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

}

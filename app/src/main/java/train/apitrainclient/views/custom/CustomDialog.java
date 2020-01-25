package train.apitrainclient.views.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.annotation.NonNull;
import android.view.Window;
import android.widget.TextView;

import train.apitrainclient.R;

public class CustomDialog extends Dialog {

    TextView dialogText;
    int counter = 0;
    boolean setToEnd = false;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_layout);
        dialogText = (TextView)findViewById(R.id.dialogText);
    }

    public void showDialog(){
        setDialogText();
    }

    public void setBooleanToEnd(boolean end){
        setToEnd = end;
    }


    public void setDialogText(){
        CountDownTimer countDownTimer = new CountDownTimer(6000,40) {
            @Override
            public void onTick(long l) {
                counter++;
                if (counter < 101){
                    dialogText.setText("Loading " + counter + " %");
                }

                if (setToEnd){
                    hide();
                }
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();
        show();
    }


    public void startHandler(final String text){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogText.setText(text);
                startSecondHandler(".");
            }
        },300);
    }

    public void startSecondHandler(final String text){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogText.append(text);
                startThirdHandler(".");
            }
        },300);
    }

    public void startThirdHandler(final String text){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogText.append(text);
                startHandler("Loading.");
            }
        },300);
    }
}

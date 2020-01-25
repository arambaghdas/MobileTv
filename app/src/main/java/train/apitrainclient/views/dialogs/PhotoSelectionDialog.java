package train.apitrainclient.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import train.apitrainclient.R;


/**
 * Dialog that ask to choose photo from either camera or gallery
 */

public class PhotoSelectionDialog extends Dialog {

    public static final int ACCESS_CAMERA = 0;
    public static final int ACCESS_GALLERY = 1;

    Context context;
    OnOptionSelectedListener optionsListener;

    TextView tvCamera;
    TextView tvGallery;

    public PhotoSelectionDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_photo_selection);

        initViews();
        initListeners();
    }

    public void setOnOptionSelectionListener(OnOptionSelectedListener onOptionSelectionListener) {
        this.optionsListener = onOptionSelectionListener;
    }

    private void initViews() {
        tvCamera = (TextView) findViewById(R.id.tv_camera);
        tvGallery = (TextView) findViewById(R.id.tv_gallery);
    }

    private void initListeners() {
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (optionsListener != null) {
                    optionsListener.optionsSelected(ACCESS_CAMERA);
                }
            }
        });

        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (optionsListener != null) {
                    optionsListener.optionsSelected(ACCESS_GALLERY);
                }
            }
        });
    }

    public interface OnOptionSelectedListener {

        void optionsSelected(int option);
    }
}

package train.apitrainclient.views.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import train.apitrainclient.R;

public class TipsCustomView extends LinearLayout {

    Context context;

    public TipsCustomView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.api_train_tips_layout, this, true);
    }
}

package com.pttrackershared.views.widgets;


/**
 * A Relative Layout that keeps its aspect ratio
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.pttrackershared.R;


public class AspectRatioRelativeLayout extends RelativeLayout {

    private float widthAspect;
    private float heightAspect;
    public AspectRatioRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    public AspectRatioRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AspectRatioRelativeLayout(Context context) {
        this(context, null);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AspectRatioRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        widthAspect = 1;
        heightAspect  = 1;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatio, defStyleAttr, 0);
        try {
            widthAspect = a.getFloat(R.styleable.AspectRatio_aspect_width,1);
            heightAspect  = a.getFloat(R.styleable.AspectRatio_aspect_height,1);
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        finally {
            a.recycle();
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int originalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int originalHeight = MeasureSpec.getSize(heightMeasureSpec);

        int calculatedHeight = (int) (originalWidth * heightAspect/widthAspect);

        int finalWidth, finalHeight;

//        if (calculatedHeight > originalHeight)
//        {
//            finalWidth = (int) (originalHeight * widthAspect / heightAspect);
//            finalHeight = originalHeight;
//        }
//        else
//        {
            finalWidth = originalWidth;
            finalHeight = calculatedHeight;
//        }

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY));
    }
}

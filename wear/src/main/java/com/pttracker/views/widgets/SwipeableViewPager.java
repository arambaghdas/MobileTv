package com.pttracker.views.widgets;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * SwipeableViewPager can be used to enable or disable swiping feature of viewpager.
 */

public class SwipeableViewPager extends ViewPager {

    //region Variables
    private boolean swipeable;
    //endregion

    //region Constructors
    public SwipeableViewPager(Context context) {
        super(context);
        swipeable = true;
    }

    public SwipeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        swipeable = true;
    }
    //endregion

    //region Overridden Methods from Base Class
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (swipeable) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (swipeable) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }
    //endregion


    //region Business Logic Specific to this Class
    public boolean isSwipeable() {
        return swipeable;
    }

    public void setSwipeable(boolean swipeable) {
        this.swipeable = swipeable;
    }
    //endregion
}

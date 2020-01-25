package com.pttracker.trainingaid.plugins;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * KeyboardUtils manages keyboard hiding feature in any activity or fragment where Keyboard hides on touching any view except an edittext
 */

public class KeyboardUtils {

    public static void SetupHidableKeyboard(Context context) {

        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            final ViewGroup rootView = (ViewGroup) ((ViewGroup) (activity)
                    .findViewById(android.R.id.content)).getChildAt(0);
            SetupHidableKeyboard(rootView, activity, null);
        }
    }

    public static void SetupHidableKeyboard(View view, Context context) {
        SetupHidableKeyboard(view, context, null);
    }

    public static void SetupHidableKeyboard(View view, final Context context, final Dialog dialog) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText) || (!view.isFocusable() && !view.isFocusableInTouchMode())) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(context, dialog);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                SetupHidableKeyboard(innerView, context, dialog);
            }
        }
    }

    private static void hideSoftKeyboard(Context context, Dialog dialog) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);

        View focusedView = null;
        if (dialog != null) {
            focusedView = dialog.getCurrentFocus();
        } else if (context instanceof Activity) {
            focusedView = ((Activity) context).getCurrentFocus();
        }

        if (focusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    focusedView.getWindowToken(), 0);
        }
    }
}
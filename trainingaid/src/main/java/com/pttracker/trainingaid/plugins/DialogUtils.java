package com.pttracker.trainingaid.plugins;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alexzaitsev.meternumberpicker.MeterView;
import com.google.android.material.snackbar.Snackbar;
import com.pttracker.trainingaid.R;
import com.pttrackershared.plugins.ResourceUtils;


/**
 * DialogUtils is a reusable class to show error and progress dialog throughout the app.
 */

public class DialogUtils {

    private static ProgressDialog progressDialog;

    /**
     * Shows error dialog provided with
     *
     * @param context as activity context and
     * @param message as text to show for error.
     */
    public static void ShowSnackbarAlert(Context context, String message) {
        ShowSnackbarAlert(context.getString(R.string.txt_ok), context, message, Snackbar.LENGTH_LONG, null);
    }

    public static void ShowSnackbarAlert(Context context, String message, final OnActionSelectedListener onActionSelectedListener) {
        ShowSnackbarAlert(context.getString(R.string.txt_ok), context, message, Snackbar.LENGTH_LONG, onActionSelectedListener);
    }

    public static void ShowSnackbarAlert(String positionButtonText, Context context, String message, final OnActionSelectedListener onActionSelectedListener) {
        ShowSnackbarAlert(positionButtonText, context, message, Snackbar.LENGTH_LONG, onActionSelectedListener);
    }

    public static void ShowSnackbarAlert(String positionButtonText, Context context, String message, int length, final OnActionSelectedListener onActionSelectedListener) {

        final ViewGroup rootView = (ViewGroup) ((ViewGroup) ((Activity) context)
                .findViewById(android.R.id.content)).getChildAt(0);

        final Snackbar snackbar = Snackbar.make(rootView, message, length);

        snackbar.setAction(positionButtonText, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onActionSelectedListener != null) {
                    onActionSelectedListener.onActionSelected();
                }
                snackbar.dismiss();
            }
        });

        if (length != Snackbar.LENGTH_INDEFINITE) {
            snackbar.setDuration(6000);
        }
        TextView textView = (TextView) snackbar.getView().findViewById(R.id.snackbar_text); //Get reference of snackbar textview
        textView.setMaxLines(10);
        snackbar.show();
    }

    public static void ShowAlertDialog(Context context, String message) {
        ShowAlertDialog(context, message, context.getString(R.string.txt_ok), null, null);
    }

    public static void ShowAlertDialog(Context context, String message, String positiveText) {
        ShowAlertDialog(context, message, positiveText, null, null);
    }

    public static void ShowAlertDialog(Context context, String message, String positiveText, OnAlertActionSelectedListener onAlertActionSelectedListener) {
        ShowAlertDialog(context, message, positiveText, null, onAlertActionSelectedListener);
    }

    public static void ShowAlertDialog(Context context, String message, String positiveText, String negativeText, final OnAlertActionSelectedListener onAlertActionSelectedListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (onAlertActionSelectedListener != null) {
                    onAlertActionSelectedListener.onNegativeSelected();
                }
            }
        });
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (onAlertActionSelectedListener != null) {
                    onAlertActionSelectedListener.onPositiveSelected();
                }
            }
        });

        final AlertDialog alertDialog = builder.create();
        new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });

        Button btnPositive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button btnNegative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);

        if (btnPositive != null) {
            btnPositive.setTextColor(ResourceUtils.getColorById(context, R.color.colorPrimary));
        }

        if (btnNegative != null) {
            btnNegative.setTextColor(ResourceUtils.getColorById(context, R.color.colorPrimary));
        }
    }

    /**
     * Shows a progress dialog provided with
     *
     * @param context as activity context and
     * @param message as text to show for progress.
     */
    public static void ShowProgress(Context context, String message) {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.setMessage(message);
        } else {
            progressDialog = ProgressDialog.show(context, "", message, true, false);
        }
    }

    public interface OnActionSelectedListener {
        void onActionSelected();
    }

    public interface OnAlertActionSelectedListener {
        void onPositiveSelected();

        void onNegativeSelected();
    }

    /**
     * Hides recently shown progress dialog
     */
    public static void HideDialog() {
        if (progressDialog != null) {
            try {
                progressDialog.dismiss();
                progressDialog = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This shows the weight input dialog with a seek bar and min max values
     * @param context
     * @param minVal
     * @param maxVal
     * @param message
     * @param callback
     */
    public static void showInputDialog(final Context context, final int minVal, final int maxVal, String message, final WeightUpdateCallback callback) {
        final View view = ((LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE)).
                inflate(R.layout.update_weight_popup, null);
        if (view != null) {
            final SeekBar bar = ((SeekBar) view.findViewById(R.id.seek_bar));
            final MeterView meter = ((MeterView) view.findViewById(R.id.meterView));
            final TextView tvStartVal = (TextView) view.findViewById(R.id.tv_seeked_val);
            final TextView tvMaxVal = (TextView) view.findViewById(R.id.tv_max_val);
            bar.setMax(maxVal - minVal);
            tvStartVal.setText(Integer.toString(minVal));
            tvMaxVal.setText(Integer.toString(maxVal));
            bar.setProgress(0);
            bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    tvStartVal.setText(Integer.toString(minVal + progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            final AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle("Update weight")
                    .setMessage(message)
                    .setView(view)
                    .setCancelable(false)
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (meter.getValue() < minVal || meter.getValue() > maxVal) {
                                Toast.makeText(context, "Please choose a value between " + minVal + " and " + maxVal, Toast.LENGTH_LONG).show();
                            } else
                                callback.updateWeight(meter.getValue());
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    })
                    .create();
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                @Override
                public void onShow(DialogInterface dialogInterface) {

                    Button b = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    b.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            if (meter.getValue() < minVal || meter.getValue() > maxVal) {
                                Toast.makeText(context, "Please choose a value between " + minVal + " and " + maxVal, Toast.LENGTH_LONG).show();
                            } else {
                                callback.updateWeight(meter.getValue());
                                dialog.dismiss();
                            }
                        }
                    });
                }
            });
            dialog.show();
        }

    }

    /**
     * interface to enable to ability to update the weight provided weight in parameter
     */
    public interface WeightUpdateCallback {
        public void updateWeight(int weight);
    }
}

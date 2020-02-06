package com.pttrackershared.utils;


import androidx.databinding.InverseMethod;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class DoubleConverter {

    @InverseMethod("toDouble")
    public static String toString(double value) {
        return String.format(Locale.US, "%.2f", value);
    }

    public static double toDouble(String value) {
        if (value.isEmpty())
            return 0.0;
        else
            return formatDecimal(Double.parseDouble(value));
    }

    private static double formatDecimal(Double value) {
        return Double.parseDouble(getDecimalFormat().format(value));
    }

     private static DecimalFormat getDecimalFormat() {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat numberFormat = (DecimalFormat) nf;
        numberFormat.applyPattern("0.00");
        return numberFormat;
    }
}

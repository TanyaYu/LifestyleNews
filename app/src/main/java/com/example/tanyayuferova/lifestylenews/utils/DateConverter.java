package com.example.tanyayuferova.lifestylenews.utils;

import android.content.Context;
import android.databinding.BindingConversion;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Tanya Yuferova on 12/17/2017.
 */

public class DateConverter {

    public static String convertDateToLongDateFormat(Date date, Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        return DateFormat.getDateInstance(DateFormat.LONG, locale).format(date);
    }

    public static String convertDateToShortDateFormat(Date date, Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        return DateFormat.getDateInstance(DateFormat.SHORT, locale).format(date);
    }
}

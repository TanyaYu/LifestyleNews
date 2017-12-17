package com.example.tanyayuferova.lifestylenews.utils;

import android.databinding.BindingConversion;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Tanya Yuferova on 12/17/2017.
 */

public class DateConverter {

    @BindingConversion
    public static String convertDateToLongDateFormat(Date date) {
        Locale locale = Locale.getDefault();
        //TODO Locale locale = context.getResources().getConfiguration().locale;
        return DateFormat.getDateInstance(DateFormat.LONG, locale).format(date);
    }
}

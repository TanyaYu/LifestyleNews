package com.tanyayuferova.lifestylenews.utils;

import android.content.Context;
import android.databinding.BindingConversion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Tanya Yuferova on 12/17/2017.
 */

public class DateConverter {

    public static String dateToLongDateFormat(Date date, Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        return DateFormat.getDateInstance(DateFormat.LONG, locale).format(date);
    }

    public static String dateToShortDateFormat(Date date, Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        return DateFormat.getDateInstance(DateFormat.SHORT, locale).format(date);
    }

    public static String dateToISO8601Format(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        //df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(date);
    }

    public static Date stringToISO8601Format(String string) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        //df.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return df.parse(string);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

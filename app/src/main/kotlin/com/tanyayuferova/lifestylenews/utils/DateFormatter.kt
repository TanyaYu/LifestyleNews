package com.tanyayuferova.lifestylenews.utils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Author: Tanya Yuferova
 * Date: 7/27/2019
 */
object DateFormatter {

    fun dateToLongDateFormat(date: Date): String {
        return DateFormat.getDateInstance(DateFormat.LONG).format(date)
    }

    fun formatShort(date: Date): String {
        return DateFormat.getDateInstance(DateFormat.SHORT).format(date)
    }

    fun dateToISO8601Format(date: Date): String {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return df.format(date)
    }

    fun stringToISO8601Format(string: String): Date? {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return try {
             df.parse(string)
        } catch (ex: ParseException) {
            ex.printStackTrace()
            null
        }
    }
}

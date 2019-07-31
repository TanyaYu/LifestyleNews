package com.tanyayuferova.lifestylenews.data.database

import androidx.room.TypeConverter
import java.util.*

/**
 * Author: Tanya Yuferova
 * Date: 7/28/2019
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
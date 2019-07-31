package com.tanyayuferova.lifestylenews.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tanyayuferova.lifestylenews.data.articles.ArticlesDao
import com.tanyayuferova.lifestylenews.domain.entity.Article

/**
 * Author: Tanya Yuferova
 * Date: 7/28/2019
 */
@Database(
    entities = [Article::class], version = 1
)
@TypeConverters(Converters::class)
abstract class ArticlesDatabase : RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao
}
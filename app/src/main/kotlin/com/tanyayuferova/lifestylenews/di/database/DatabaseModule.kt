package com.tanyayuferova.lifestylenews.di.database

import android.content.Context
import androidx.room.Room
import com.tanyayuferova.lifestylenews.data.articles.ArticlesDao
import com.tanyayuferova.lifestylenews.data.database.ArticlesDatabase
import com.tanyayuferova.lifestylenews.data.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Author: Tanya Yuferova
 * Date: 7/27/2019
 */
@Module
object DatabaseModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideDatabase(context: Context): ArticlesDatabase {
        return Room.databaseBuilder(
            context,
            ArticlesDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideArticlesDao(database: ArticlesDatabase): ArticlesDao {
        return database.articlesDao()
    }
}
 
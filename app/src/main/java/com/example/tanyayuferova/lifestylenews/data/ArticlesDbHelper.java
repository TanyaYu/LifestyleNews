package com.example.tanyayuferova.lifestylenews.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tanyayuferova.lifestylenews.data.ArticlesContract.*;

/**
 * Created by Tanya Yuferova on 12/22/2017.
 */

public class ArticlesDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "articles.db";
    private static final int DATABASE_VERSION = 1;

    public ArticlesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createArticlesTable(db);
    }

    private void createArticlesTable(SQLiteDatabase db) {
        final String SQL_CREATE_ARTICLES_TABLE =
                "CREATE TABLE " + ArticleEntry.TABLE_NAME + " (" +
                        ArticleEntry._ID + " INTEGER PRIMARY KEY, " +
                        ArticleEntry.COLUMN_SOURCE_NAME + " varchar(200) NULL, " +
                        ArticleEntry.COLUMN_AUTHOR + " varchar(500) NULL, " +
                        ArticleEntry.COLUMN_TITLE + " varchar(500) NULL, " +
                        ArticleEntry.COLUMN_DESCRIPTION + " text NULL, " +
                        ArticleEntry.COLUMN_URL + " varchar(500) NULL, " +
                        ArticleEntry.COLUMN_URL_TO_IMAGE + " varchar(500) NULL, " +
                        ArticleEntry.COLUMN_PUBLISHED_AT + " datetime NOT NULL," +
                        ArticleEntry.COLUMN_FAVORITE + " boolean NOT NULL," +
                        ArticleEntry.COLUMN_FAVORITE_DATE + " datetime NULL); ";
        db.execSQL(SQL_CREATE_ARTICLES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ArticleEntry.TABLE_NAME);
        onCreate(db);
    }
}

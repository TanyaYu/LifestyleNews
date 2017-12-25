package com.example.tanyayuferova.lifestylenews.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.example.tanyayuferova.lifestylenews.entity.Article;

import java.util.Date;
import java.util.List;

import static com.example.tanyayuferova.lifestylenews.data.ArticlesContract.ArticleEntry.*;

/**
 * Created by Tanya Yuferova on 12/23/2017.
 */

public class DataUtils {

    /**
     * Creates content values from articles list
     * @param articles
     * @return
     */
    public static ContentValues[] getContentValues(List<Article> articles) {
        ContentValues[] result = new ContentValues[articles.size()];
        for (int i = 0; i < articles.size(); i++) {
            result[i] = getContentValues(articles.get(i));
        }
        return result;
    }

    /**
     * Creates content values from article
     * @param article
     * @return
     */
    public static ContentValues getContentValues(Article article) {
        ContentValues result = new ContentValues();
        result.put(COLUMN_TITLE, article.getTitle());
        result.put(COLUMN_DESCRIPTION, article.getDescription());
        result.put(COLUMN_SOURCE_NAME, article.getSourceName());
        result.put(COLUMN_AUTHOR, article.getAuthor());
        result.put(COLUMN_PUBLISHED_AT,
                DateConverter.dateToISO8601Format(article.getPublished() == null ? new Date() : article.getPublished()));
        result.put(COLUMN_URL, article.getUrl());
        result.put(COLUMN_URL_TO_IMAGE, article.getUrlToImage());
        result.put(COLUMN_FAVORITE, article.isFavorite());
        if (article.getAddedToFavored() != null)
            result.put(COLUMN_FAVORITE_DATE, DateConverter.dateToISO8601Format(article.getAddedToFavored()));
        //result.put(BaseColumns._ID, article.getId());
        return result;
    }

    /**
     * Reads article from cursor
     * @param cursor
     * @return
     */
    public static Article getArticle(Cursor cursor) {
        Article result = new Article();
        result.setId(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
        result.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
        result.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
        result.setSourceName(cursor.getString(cursor.getColumnIndex(COLUMN_SOURCE_NAME)));
        result.setAuthor(cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)));
        result.setPublished(DateConverter.stringToISO8601Format(cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHED_AT))));
        result.setUrl(cursor.getString(cursor.getColumnIndex(COLUMN_URL)));
        result.setUrlToImage(cursor.getString(cursor.getColumnIndex(COLUMN_URL_TO_IMAGE)));
        result.setFavorite(cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITE)) > 0);
        if(cursor.getString(cursor.getColumnIndex(COLUMN_FAVORITE_DATE)) != null)
        result.setAddedToFavored(DateConverter.stringToISO8601Format(cursor.getString(cursor.getColumnIndex(COLUMN_FAVORITE_DATE))));
        return result;
    }
}

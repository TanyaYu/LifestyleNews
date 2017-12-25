package com.example.tanyayuferova.lifestylenews.sync;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.text.format.DateUtils;

import com.example.tanyayuferova.lifestylenews.data.ArticlesContract;
import com.example.tanyayuferova.lifestylenews.entity.Article;
import com.example.tanyayuferova.lifestylenews.utils.DataUtils;
import com.example.tanyayuferova.lifestylenews.utils.NetworkUtils;
import com.example.tanyayuferova.lifestylenews.utils.NotificationUtils;
import com.example.tanyayuferova.lifestylenews.utils.PreferencesUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.tanyayuferova.lifestylenews.data.ArticlesContract.*;

/**
 * Created by Tanya Yuferova on 12/23/2017.
 */

public class SyncTask {

    public static final int ARTICLES_COUNT_LIMIT = 100;
    public static final float ARTICLES_PER_HOUR_KOEF = 0.4f;

    /**
     * Loads new articles
     * @param context
     */
    synchronized public static void syncLoadNewArticles(Context context) {
        asyncLoadNewArticles(context);
    }

    /**
     * Loads new articles
     * @param context
     */
    public static void asyncLoadNewArticles(Context context) {

        try {
            Date lastDateUpdated = getLastTimeUpdatedDate(context);
            Date currentDate = Calendar.getInstance().getTime();
            int articlesLimit = getArticlesCountLimitForPeriod(lastDateUpdated);

            List<Article> newArticles = NetworkUtils.loadArticles(context, lastDateUpdated, currentDate, articlesLimit);
            if (newArticles == null || newArticles.size() == 0)
                return;

            ContentResolver contentResolver = context.getContentResolver();

            int totalNewArticles = newArticles.size();
            int countCurrent = countCurrentArticles(contentResolver);
            int deleteArticles = totalNewArticles - ARTICLES_COUNT_LIMIT + countCurrent;

            if(deleteArticles > 0) {
                deleteArticles(contentResolver, deleteArticles);
            }

            insertArticles(contentResolver, newArticles);

            boolean notificationsEnabled = PreferencesUtils.getNotificationEnabled(context);
            if (notificationsEnabled) {
                NotificationUtils.notifyUserOfNewArticles(context, totalNewArticles);
            }

            PreferencesUtils.setLastTimeRefresh(context, currentDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Fully refresh articles list
     * @param context
     */
    public static void asyncRefreshAllArticles(Context context) {

        try {
            Calendar now = Calendar.getInstance();
            Date currentDate = Calendar.getInstance().getTime();
            now.add(Calendar.MONTH, -1);
            Date lastDateUpdated = now.getTime();

            List<Article> newArticles = NetworkUtils.loadArticles(context, lastDateUpdated, currentDate, ARTICLES_COUNT_LIMIT);
            if (newArticles == null || newArticles.size() == 0)
                return;

            ContentResolver contentResolver = context.getContentResolver();

            int totalNewArticles = newArticles.size();
            int countCurrent = countCurrentArticles(contentResolver);

            deleteArticles(contentResolver, countCurrent);

            insertArticles(contentResolver, newArticles);

            boolean notificationsEnabled = PreferencesUtils.getNotificationEnabled(context);
            if (notificationsEnabled) {
                NotificationUtils.notifyUserOfNewArticles(context, totalNewArticles);
            }

            PreferencesUtils.setLastTimeRefresh(context, currentDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Date getLastTimeUpdatedDate(Context context) {
        Date lastDateUpdated = PreferencesUtils.getLastTimeRefresh(context);
        if (lastDateUpdated == null) {
            //If it has not ever been updated, we pick date a month ago
            Calendar now = Calendar.getInstance();
            now.add(Calendar.MONTH, -1);
            lastDateUpdated = now.getTime();
        }
        return lastDateUpdated;
    }

    /**
     * Gets articles amount necessary to load for period from fromDate to now
     * @param fromDate
     * @return
     */
    private static int getArticlesCountLimitForPeriod(Date fromDate) {
        Calendar now = Calendar.getInstance();
        long hours = (now.getTimeInMillis() - fromDate.getTime()) / DateUtils.HOUR_IN_MILLIS;
        int result = (int) (hours * ARTICLES_PER_HOUR_KOEF);
        return result > ARTICLES_COUNT_LIMIT ? ARTICLES_COUNT_LIMIT : result;
    }

    private static int countCurrentArticles(ContentResolver contentResolver) {
        Cursor cursor = contentResolver.query(
                ArticlesContract.CONTENT_RECENT_URI,
                new String[]{ArticleEntry._ID}, null, null, null);
        if(cursor == null)
            return 0;
        int result = cursor.getCount();
        cursor.close();
        return result;
    }

    /**
     * Deletes last deleteArticles articles
     * @param contentResolver
     * @param deleteArticles
     */
    private static void deleteArticles(ContentResolver contentResolver, int deleteArticles) {
        contentResolver.delete(
                ArticlesContract.CONTENT_RECENT_URI,
                ArticleEntry._ID + " in (select " + ArticleEntry._ID +
                        " from " + ArticleEntry.TABLE_NAME + " where " +
                        ArticleEntry.COLUMN_FAVORITE + " = 0 order by "
                        + ArticleEntry.COLUMN_PUBLISHED_AT + " asc limit " + deleteArticles + " )",
                null);
    }

    private static void insertArticles(ContentResolver contentResolver, List<Article> articles) {
        contentResolver.bulkInsert(
                ArticlesContract.CONTENT_RECENT_URI,
                DataUtils.getContentValues(articles));
    }
}

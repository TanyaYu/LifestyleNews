package com.tanyayuferova.lifestylenews.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.tanyayuferova.lifestylenews.R;
import com.tanyayuferova.lifestylenews.data.ArticlesContract;
import com.tanyayuferova.lifestylenews.ui.activity.ArticlesListActivity;

/**
 * Created by Tanya Yuferova on 12/23/2017.
 */

public class NotificationUtils {

    private static final int NEW_ARTICLES_NOTIFICATION_ID = 100;
    private static final int PENDING_INTENT_ID = 200;

    /**
     * Constructs and displays a notification for the new articles
     * @param context
     */
    public static void notifyUserOfNewArticles(Context context, int articlesQuantity) {

        if(articlesQuantity <=0)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createReminderChannel(context);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, context.getString(R.string.notification_new_articles_channel_id))
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setLargeIcon(largeIcon(context, R.drawable.ic_notification_icon))
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getResources().getQuantityString(R.plurals.numberOfArticlesLoaded, articlesQuantity, articlesQuantity))
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NEW_ARTICLES_NOTIFICATION_ID, notificationBuilder.build());
    }

    @TargetApi(Build.VERSION_CODES.O)
    private static void createReminderChannel(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(context.getString(R.string.notification_new_articles_channel_id),
                context.getString(R.string.notification_new_articles_channel_title), notificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(false);
        channel.enableVibration(true);
        channel.setShowBadge(true);
        notificationManager.createNotificationChannel(channel);
    }

    /**
     * Content intent that opens after user tap on notification
     * @param context
     * @return
     */
    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, ArticlesListActivity.class);
        startActivityIntent.setData(ArticlesContract.CONTENT_RECENT_URI);
        startActivityIntent.putExtra(ArticlesListActivity.EXTRA_LIST_TITLE, context.getString(R.string.recent_title));
        return PendingIntent.getActivity(
                context,
                PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap largeIcon(Context context, int resource) {
        Resources res = context.getResources();
        return BitmapFactory.decodeResource(res, resource);
    }
}

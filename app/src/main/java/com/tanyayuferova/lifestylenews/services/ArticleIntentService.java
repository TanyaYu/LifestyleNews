package com.tanyayuferova.lifestylenews.services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.tanyayuferova.lifestylenews.R;
import com.tanyayuferova.lifestylenews.ui.widget.FavoriteArticlesWidget;
import com.tanyayuferova.lifestylenews.ui.widget.RecentArticlesWidget;

/**
 * Intent service for updating widgets
 *
 * Created by Tanya Yuferova on 11/14/2017.
 */

public class ArticleIntentService extends IntentService {

    public static final String ACTION_UPDATE_RECENT_ARTICLES_WIDGET = "action.update_recent_articles_widget";
    public static final String ACTION_UPDATE_FAVORITE_ARTICLES_WIDGET = "action.update_favorite_articles_widget";

    public ArticleIntentService() {
        super("ArticleIntentService");
    }


    public static void startActionUpdateRecentArticlesWidgets(Context context) {
        Intent intent = new Intent(context, ArticleIntentService.class);
        intent.setAction(ACTION_UPDATE_RECENT_ARTICLES_WIDGET);
        context.startService(intent);
    }

    public static void startActionUpdateFavoriteArticlesWidgets(Context context) {
        Intent intent = new Intent(context, ArticleIntentService.class);
        intent.setAction(ACTION_UPDATE_FAVORITE_ARTICLES_WIDGET);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_RECENT_ARTICLES_WIDGET.equals(action)) {
                handleActionUpdateRecentArticlesWidgets();
            } else if (ACTION_UPDATE_FAVORITE_ARTICLES_WIDGET.equals(action)) {
                handleActionUpdateFavoriteArticlesWidgets();
            }
        }
    }

    private void handleActionUpdateRecentArticlesWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ArticleIntentService.this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(ArticleIntentService.this, RecentArticlesWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        RecentArticlesWidget.updateWidgets(ArticleIntentService.this, appWidgetManager, appWidgetIds);
    }

    private void handleActionUpdateFavoriteArticlesWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ArticleIntentService.this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(ArticleIntentService.this, FavoriteArticlesWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        FavoriteArticlesWidget.updateWidgets(ArticleIntentService.this, appWidgetManager, appWidgetIds);
    }
}

package com.example.tanyayuferova.lifestylenews.services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.tanyayuferova.lifestylenews.R;
import com.example.tanyayuferova.lifestylenews.ui.widget.RecentArticlesWidget;

/**
 * Created by Tanya Yuferova on 11/14/2017.
 */

public class ArticleIntentService extends IntentService {

    public static final String ACTION_UPDATE_ARTICLE_WIDGET = "action.update_article_widget";

    public ArticleIntentService() {
        super("ArticleIntentService");
    }

    /**
     * Starts this service to perform UpdateRecipeWidgets action with the given parameters
     *
     * @see IntentService
     */
    public static void startActionUpdateRecipeWidgets(Context context) {
        Intent intent = new Intent(context, ArticleIntentService.class);
        intent.setAction(ACTION_UPDATE_ARTICLE_WIDGET);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_ARTICLE_WIDGET.equals(action)) {
                handleActionUpdateRecipeWidgets();
            }
        }
    }

    private void handleActionUpdateRecipeWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ArticleIntentService.this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(ArticleIntentService.this, RecentArticlesWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        RecentArticlesWidget.updateRecipeWidgets(ArticleIntentService.this, appWidgetManager, appWidgetIds);
    }
}

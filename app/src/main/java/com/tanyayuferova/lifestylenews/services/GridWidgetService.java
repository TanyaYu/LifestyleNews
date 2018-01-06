package com.tanyayuferova.lifestylenews.services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.tanyayuferova.lifestylenews.R;
import com.tanyayuferova.lifestylenews.data.ArticlesContract;
import com.tanyayuferova.lifestylenews.entity.Article;
import com.tanyayuferova.lifestylenews.sync.ArticlesAsyncTaskLoader;
import com.tanyayuferova.lifestylenews.ui.activity.ArticleDetailsActivity;
import com.tanyayuferova.lifestylenews.utils.DateConverter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service providing views for Favorite and Recent widgets
 *
 * Created by Tanya Yuferova on 11/14/2017.
 */

public class GridWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext(), intent.getData());
    }
}

/**
 * Provides views for Favorite and Recent widgets
 */
class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    List<Article> data;
    // How many articles to display
    int displayNumber;
    Uri uri;


    public GridRemoteViewsFactory(Context applicationContext, Uri uri) {
        context = applicationContext;
        displayNumber = context.getResources().getInteger(R.integer.articles_list_rows) * context.getResources().getInteger(R.integer.articles_list_columns);
        this.uri = uri;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        new ArticlesAsyncTaskLoader(context, uri, displayNumber) {
            @Override
            public void deliverResult(List<Article> data) {
                super.deliverResult(data);
                GridRemoteViewsFactory.this.data = data;
            }
        }.startLoading();
    }

    @Override
    public void onDestroy() {
        data = null;
    }

    @Override
    public int getCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (data == null)
            return null;


        Article article = data.get(position);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recent_articles_widget);

        Bitmap bitmap = null;
        try {
            bitmap = Picasso.with(context).load(article.getUrlToImage()).get();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Article image
        if (bitmap == null)
            views.setImageViewResource(R.id.iv_image, R.drawable.ic_newspaper_color);
        else
            views.setImageViewBitmap(R.id.iv_image, bitmap);

        //title
        views.setTextViewText(R.id.tv_title, article.getTitle());

        // RSource and published date
        String sourcePublishedOnDate = (article.getSourceName() == null ? "" : article.getSourceName())
                + (article.getPublished()==null ? "" :
                context.getResources().getString(R.string.on_date, DateConverter.dateToShortDateFormat(article.getPublished(), context)));
        views.setTextViewText(R.id.tv_source_published_on_date, sourcePublishedOnDate);

        // Pending intent when click on view
        Intent fillInIntent = new Intent();
        fillInIntent.putParcelableArrayListExtra(ArticleDetailsActivity.EXTRA_ARTICLES, new ArrayList<Parcelable>(data));
        fillInIntent.putExtra(ArticleDetailsActivity.EXTRA_SELECTED_INDEX, data.indexOf(article));
        views.setOnClickFillInIntent(R.id.iv_image, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

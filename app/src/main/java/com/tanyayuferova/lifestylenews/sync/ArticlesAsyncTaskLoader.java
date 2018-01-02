package com.tanyayuferova.lifestylenews.sync;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;

import com.tanyayuferova.lifestylenews.entity.Article;
import com.tanyayuferova.lifestylenews.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Loads articles from ContentProvider
 * Created by Tanya Yuferova on 12/17/2017.
 */

public class ArticlesAsyncTaskLoader extends AsyncTaskLoader<List<Article>> {

    List<Article> data = null;
    private int countLimit;
    private Uri uri;

    public ArticlesAsyncTaskLoader(Context context, Uri uri) {
        super(context);
        this.uri = uri;
    }

    public ArticlesAsyncTaskLoader(Context context, Uri uri, int countLimit) {
        this(context, uri);
        this.countLimit = countLimit;
    }

    @Override
    protected void onStartLoading() {
        if (data != null) {
            deliverResult(data);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<Article> loadInBackground() {
        String sortOrder = countLimit == 0 ? "" : " limit " + countLimit;
        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null,null, sortOrder);

        List<Article> result = new ArrayList<>();
        if(cursor == null || cursor.getCount() <=0)
            return result;

        while (cursor.moveToNext()) {
            result.add(DataUtils.getArticle(cursor));
        }
        cursor.close();
        return result;
    }

    public void deliverResult(List<Article> data) {
        this.data = data;
        super.deliverResult(data);
    }
}

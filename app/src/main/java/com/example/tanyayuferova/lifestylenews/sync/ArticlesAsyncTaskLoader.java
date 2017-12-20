package com.example.tanyayuferova.lifestylenews.sync;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;

import com.example.tanyayuferova.lifestylenews.entity.Article;
import com.example.tanyayuferova.lifestylenews.utils.TestUtils;

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
        return TestUtils.getTestData(countLimit == 0 ? 100 : countLimit);
    }

    public void deliverResult(List<Article> data) {
        this.data = data;
        super.deliverResult(data);
    }
}

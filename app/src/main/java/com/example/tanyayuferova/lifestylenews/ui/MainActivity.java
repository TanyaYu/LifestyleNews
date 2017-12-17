package com.example.tanyayuferova.lifestylenews.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.tanyayuferova.lifestylenews.R;
import com.example.tanyayuferova.lifestylenews.entity.Article;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Article>>,
        ArticlesAdapter.OnClickArticleHandler {

    private RecyclerView recentRV;
    private RecyclerView favoriteRV;
    private Toolbar toolbar;
    private ArticlesAdapter recentAdapter;
    private ArticlesAdapter favoriteAdapter;

    private int columns;
    private int maxArticles;

    private static final int RECENT_ARTICLES_LOADER_ID = 11;
    private static final int FAVORITE_ARTICLES_LOADER_ID = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recentRV = findViewById(R.id.rv_recent);
        favoriteRV = findViewById(R.id.rv_favorite);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        columns = getResources().getInteger(R.integer.articles_list_columns);
        maxArticles = columns * getResources().getInteger(R.integer.articles_list_rows);

        initRecyclerView(recentRV, recentAdapter = new ArticlesAdapter(this));
        initRecyclerView(favoriteRV, favoriteAdapter = new ArticlesAdapter(this));

        getSupportLoaderManager().initLoader(RECENT_ARTICLES_LOADER_ID, null, MainActivity.this);
        getSupportLoaderManager().initLoader(FAVORITE_ARTICLES_LOADER_ID, null, MainActivity.this);
    }

    private void initRecyclerView(RecyclerView recyclerView, ArticlesAdapter adapter) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, columns);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case RECENT_ARTICLES_LOADER_ID:
                return new ArticlesAsyncTaskLoader(this, null, maxArticles);

            case FAVORITE_ARTICLES_LOADER_ID:
                return new ArticlesAsyncTaskLoader(this, null, maxArticles);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        switch (loader.getId()) {
            case RECENT_ARTICLES_LOADER_ID:
                recentAdapter.setData(data);
                break;

            case FAVORITE_ARTICLES_LOADER_ID:
                favoriteAdapter.setData(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {

    }

    public void onCaptionClick(View view) {
        int viewId = view.getId();
        Uri uri = null;
        String title = null;

        switch (viewId) {
            case R.id.tv_recent_caption:
                uri = null;
                title = getString(R.string.recent_title);
                break;
            case R.id.tv_favorite_caption:
                uri = null;
                title = getString(R.string.favorite_title);
                break;
        }

        Intent intent = new Intent(this, ArticlesListActivity.class);
        intent.setData(uri);
        intent.putExtra(ArticlesListActivity.EXTRA_LIST_TITLE, title);
        startActivity(intent);
    }

    @Override
    public void onClickArticle(Article article) {

    }
}

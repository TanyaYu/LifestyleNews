package com.example.tanyayuferova.lifestylenews.ui;

import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.tanyayuferova.lifestylenews.R;
import com.example.tanyayuferova.lifestylenews.entity.Article;

import java.util.List;

public class ArticlesListActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Article>>,
        ArticlesAdapter.OnClickArticleHandler {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ArticlesAdapter adapter;

    private int columns;
    private Uri uri;

    private static final int ARTICLES_LOADER_ID = 20;
    public static final String EXTRA_LIST_TITLE = "extra.list_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_list);

        recyclerView = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar);

        columns = getResources().getInteger(R.integer.articles_list_columns);
        uri = getIntent().getData();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(EXTRA_LIST_TITLE));

        adapter = new ArticlesAdapter(this);
        initRecyclerView(recyclerView, adapter);
        getSupportLoaderManager().initLoader(ARTICLES_LOADER_ID, null, ArticlesListActivity.this);
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
            case ARTICLES_LOADER_ID:
                return new ArticlesAsyncTaskLoader(this, uri);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        switch (loader.getId()) {
            case ARTICLES_LOADER_ID:
                adapter.setData(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {

    }

    @Override
    public void onClickArticle(Article article) {

    }
}

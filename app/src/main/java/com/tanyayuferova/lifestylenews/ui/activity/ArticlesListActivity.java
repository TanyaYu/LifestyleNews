package com.tanyayuferova.lifestylenews.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.tanyayuferova.lifestylenews.R;
import com.tanyayuferova.lifestylenews.databinding.ActivityArticlesListBinding;
import com.tanyayuferova.lifestylenews.sync.SyncTask;
import com.tanyayuferova.lifestylenews.ui.fragment.ArticlesListFragment;

public class ArticlesListActivity extends AppCompatActivity
implements ArticlesListFragment.LoaderCallback {

    private ActivityArticlesListBinding binding;
    protected ArticlesListFragment articlesListFragment;
    protected Uri uri;
    public static final String EXTRA_LIST_TITLE = "extra.list_title";
    public static final int REQUEST_CODE_TOPICS_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_articles_list);
        uri = getIntent().getData();

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(EXTRA_LIST_TITLE));

        binding.swipeRefresh.setRefreshing(true);
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadArticlesAsyncTask().execute(false);
            }
        });
        initArticlesListFragment();
    }

    protected void initArticlesListFragment() {
        articlesListFragment = (ArticlesListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_list);
        if(articlesListFragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_list, articlesListFragment = ArticlesListFragment.newInstance(uri))
                    .commit();
        }
        articlesListFragment.setLoaderCallback(this);
    }

    @Override
    public void onLoadFinished(boolean success) {
        binding.swipeRefresh.setRefreshing(false);
        if(!success) {
            Snackbar snackbar = Snackbar.make(binding.coordinator, R.string.error_loading_data, Snackbar.LENGTH_LONG);
            ((TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
            snackbar.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_topics:
                startActivityForResult(new Intent(this, TopicsActivity.class), REQUEST_CODE_TOPICS_ACTIVITY);
                return true;

            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_TOPICS_ACTIVITY && resultCode == RESULT_OK) {
            new LoadArticlesAsyncTask().execute(true);
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    private class LoadArticlesAsyncTask extends AsyncTask<Boolean, Void, Void> {

        @Override
        protected Void doInBackground(Boolean... booleans) {
            //Refresh list completely or only load new articles
            if(booleans[0])
                SyncTask.asyncRefreshAllArticles(ArticlesListActivity.this);
            else SyncTask.asyncLoadNewArticles(ArticlesListActivity.this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            articlesListFragment.refreshLoader();
        }

        @Override
        protected void onPreExecute() {
            binding.swipeRefresh.setRefreshing(true);
        }
    }
}

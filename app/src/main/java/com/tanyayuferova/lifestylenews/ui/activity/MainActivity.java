package com.tanyayuferova.lifestylenews.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tanyayuferova.lifestylenews.R;
import com.tanyayuferova.lifestylenews.data.ArticlesContract;
import com.tanyayuferova.lifestylenews.databinding.ActivityMainBinding;
import com.tanyayuferova.lifestylenews.sync.SyncTask;
import com.tanyayuferova.lifestylenews.sync.SyncUtils;
import com.tanyayuferova.lifestylenews.ui.fragment.ArticlesListFragment;
import com.tanyayuferova.lifestylenews.utils.PreferencesUtils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    protected ArticlesListFragment articlesRecentFragment;
    protected ArticlesListFragment articlesFavoriteFragment;
    private boolean refreshStarted = true;
    private boolean showError = true;

    public static final int REQUEST_CODE_TOPICS_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        SyncUtils.scheduleFirebaseJobDispatcherSync(this);

        setSupportActionBar(binding.toolbar);
        binding.swipeRefreshLayout.setRefreshing(true);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadArticlesAsyncTask().execute(false);
            }
        });

        int maxArticles = getResources().getInteger(R.integer.articles_list_columns) * getResources().getInteger(R.integer.articles_list_rows);

        // Init recent articles fragment
        articlesRecentFragment = (ArticlesListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_recent_list);
        if(articlesRecentFragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_recent_list,
                            articlesRecentFragment = ArticlesListFragment.newInstance(ArticlesContract.CONTENT_RECENT_URI, maxArticles))
                    .commit();
        }
        articlesRecentFragment.setLoaderCallback(new ArticlesListFragment.LoaderCallback() {
            @Override
            public void onLoadFinished(boolean success) {
                binding.ivRecentArrow.setVisibility(success ? View.VISIBLE : View.GONE);
                binding.tvRecentCaption.setVisibility(success ? View.VISIBLE : View.GONE);
                showError &= !success;
                MainActivity.this.onLoadFinished();
            }
        });

        // Init favorite articles fragment
        articlesFavoriteFragment = (ArticlesListFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_favorite_list);
        if(articlesFavoriteFragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_favorite_list,
                            articlesFavoriteFragment = ArticlesListFragment.newInstance(ArticlesContract.CONTENT_FAVORITE_URI, maxArticles))
                    .commit();
        }
        articlesFavoriteFragment.setLoaderCallback(new ArticlesListFragment.LoaderCallback() {
            @Override
            public void onLoadFinished(boolean success) {
                binding.ivFavoriteArrow.setVisibility(success ? View.VISIBLE : View.GONE);
                binding.tvFavoriteCaption.setVisibility(success ? View.VISIBLE : View.GONE);
                showError &= !success;
                MainActivity.this.onLoadFinished();
            }
        });

        if(PreferencesUtils.getTopicsPreferences(this).size() == 0){
            startActivityForResult(new Intent(this, TopicsActivity.class), REQUEST_CODE_TOPICS_ACTIVITY);
        }
    }

    public void onFavoriteClick(View view) {
        startArticlesListActivity(ArticlesContract.CONTENT_FAVORITE_URI, getString(R.string.favorite_title));
    }

    public void onRecentClick(View view) {
        startArticlesListActivity(ArticlesContract.CONTENT_RECENT_URI, getString(R.string.recent_title));
    }

    protected void startArticlesListActivity(final Uri data, final String title) {
        articlesFavoriteFragment.runRecyclerViewAnimation(R.anim.layout_animation_fall_down);
        articlesRecentFragment.runRecyclerViewAnimation(R.anim.layout_animation_fall_down);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, ArticlesListActivity.class);
                intent.setData(data);
                intent.putExtra(ArticlesListActivity.EXTRA_LIST_TITLE, title);
                startActivity(intent);
            }
        }, getResources().getInteger(R.integer.anim_duration_medium));
    }

    protected void onLoadFinished() {
        if(refreshStarted) {
            // Need to wait for the second loader
            refreshStarted = false;
            return;
        }

        if(showError) {
            Snackbar snackbar = Snackbar.make(binding.coordinator, R.string.error_loading_data, Snackbar.LENGTH_LONG);
            ((TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
            snackbar.show();
        }

        binding.swipeRefreshLayout.setRefreshing(false);
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
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class LoadArticlesAsyncTask extends AsyncTask<Boolean, Void, Void> {

        @Override
        protected Void doInBackground(Boolean... booleans) {
            //Refresh list completely or only load new articles
            if(booleans[0])
                SyncTask.asyncRefreshAllArticles(MainActivity.this);
            else SyncTask.asyncLoadNewArticles(MainActivity.this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            articlesFavoriteFragment.refreshLoader();
            articlesRecentFragment.refreshLoader();
        }

        @Override
        protected void onPreExecute() {
            refreshStarted = showError = true;
        }
    }
}

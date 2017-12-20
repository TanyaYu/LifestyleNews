package com.example.tanyayuferova.lifestylenews.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tanyayuferova.lifestylenews.R;
import com.example.tanyayuferova.lifestylenews.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArticlesListFragment articlesRecentFragment;
    private ArticlesListFragment articlesFavoriteFragment;
    private boolean refreshStarted = true;
    private boolean showError = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(binding.toolbar);
        binding.swipeRefreshLayout.setRefreshing(true);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshStarted = showError = true;
                articlesFavoriteFragment.refreshLoader();
                articlesRecentFragment.refreshLoader();
            }
        });

        int maxArticles = getResources().getInteger(R.integer.articles_list_columns) * getResources().getInteger(R.integer.articles_list_rows);

        // Init recent articles fragment
        articlesRecentFragment = (ArticlesListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_recent_list);
        if(articlesRecentFragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_recent_list, articlesRecentFragment = ArticlesListFragment.newInstance(null, maxArticles))
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
                    .replace(R.id.fragment_favorite_list, articlesFavoriteFragment = ArticlesListFragment.newInstance(null, maxArticles))
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
    }

    public void onFavoriteClick(View view) {
        startArticlesListActivity(null, getString(R.string.favorite_title));
    }

    public void onRecentClick(View view) {
        startArticlesListActivity(null, getString(R.string.recent_title));
    }

    protected void startArticlesListActivity(Uri data, String title) {
        Intent intent = new Intent(this, ArticlesListActivity.class);
        intent.setData(data);
        intent.putExtra(ArticlesListActivity.EXTRA_LIST_TITLE, title);
        startActivity(intent);
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
}

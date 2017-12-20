package com.example.tanyayuferova.lifestylenews.ui.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tanyayuferova.lifestylenews.R;
import com.example.tanyayuferova.lifestylenews.databinding.ActivityArticlesListBinding;
import com.example.tanyayuferova.lifestylenews.ui.fragment.ArticlesListFragment;

public class ArticlesListActivity extends AppCompatActivity
implements ArticlesListFragment.LoaderCallback {

    private ActivityArticlesListBinding binding;
    private ArticlesListFragment articlesListFragment;
    private Uri uri;
    public static final String EXTRA_LIST_TITLE = "extra.list_title";

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
                articlesListFragment.refreshLoader();
            }
        });

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
}

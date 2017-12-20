package com.example.tanyayuferova.lifestylenews.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.tanyayuferova.lifestylenews.R;
import com.example.tanyayuferova.lifestylenews.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(binding.toolbar);

        int maxArticles = getResources().getInteger(R.integer.articles_list_columns) * getResources().getInteger(R.integer.articles_list_rows);

        if(getSupportFragmentManager().findFragmentById(R.id.fragment_recent_list) == null)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_recent_list, ArticlesListFragment.newInstance(null, maxArticles))
                    .commit();

        if(getSupportFragmentManager().findFragmentById(R.id.fragment_favorite_list) == null)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_favorite_list, ArticlesListFragment.newInstance(null, maxArticles))
                    .commit();
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
}

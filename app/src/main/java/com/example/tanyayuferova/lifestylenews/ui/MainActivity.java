package com.example.tanyayuferova.lifestylenews.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.tanyayuferova.lifestylenews.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

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
}

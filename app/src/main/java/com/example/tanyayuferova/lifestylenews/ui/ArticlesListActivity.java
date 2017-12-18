package com.example.tanyayuferova.lifestylenews.ui;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.tanyayuferova.lifestylenews.R;

public class ArticlesListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Uri uri;
    public static final String EXTRA_LIST_TITLE = "extra.list_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_list);

        uri = getIntent().getData();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(EXTRA_LIST_TITLE));

        if(getSupportFragmentManager().findFragmentById(R.id.fragment_list) == null)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_list, ArticlesListFragment.newInstance(uri))
                    .commit();
    }
}

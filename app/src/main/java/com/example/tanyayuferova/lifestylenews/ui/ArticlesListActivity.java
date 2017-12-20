package com.example.tanyayuferova.lifestylenews.ui;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tanyayuferova.lifestylenews.R;
import com.example.tanyayuferova.lifestylenews.databinding.ActivityArticlesListBinding;

public class ArticlesListActivity extends AppCompatActivity {

    private ActivityArticlesListBinding binding;
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

        if(getSupportFragmentManager().findFragmentById(R.id.fragment_list) == null)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_list, ArticlesListFragment.newInstance(uri))
                    .commit();
    }
}

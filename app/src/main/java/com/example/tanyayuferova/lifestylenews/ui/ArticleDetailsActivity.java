package com.example.tanyayuferova.lifestylenews.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tanyayuferova.lifestylenews.R;
import com.example.tanyayuferova.lifestylenews.entity.Article;

import java.util.List;

public class ArticleDetailsActivity extends AppCompatActivity {

    private ViewPager pager;
    private List<Article> articles;
    public static final String EXTRA_ARTICLES = "extra.articles";
    public static final String EXTRA_SELECTED_INDEX = "extra.selected_index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        pager = findViewById(R.id.pager);

        articles = getIntent().getParcelableArrayListExtra(EXTRA_ARTICLES);
        int selectedIndex = getIntent().getIntExtra(EXTRA_SELECTED_INDEX, 0);

        pager.setAdapter(new ArticleDetailsPagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(selectedIndex);
    }

    public class ArticleDetailsPagerAdapter extends FragmentPagerAdapter {

        public ArticleDetailsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ArticleDetailsFragment.newInstance(articles.get(position));
        }

        @Override
        public int getCount() {
            return articles.size();
        }
    }
}

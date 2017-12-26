package com.example.tanyayuferova.lifestylenews.ui.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.tanyayuferova.lifestylenews.R;
import com.example.tanyayuferova.lifestylenews.databinding.ActivityArticleDetailsBinding;
import com.example.tanyayuferova.lifestylenews.entity.Article;
import com.example.tanyayuferova.lifestylenews.ui.fragment.ArticleDetailsFragment;

import java.util.List;

public class ArticleDetailsActivity extends AppCompatActivity {

    private ActivityArticleDetailsBinding binding;
    private List<Article> articles;
    public static final String EXTRA_ARTICLES = "extra.articles";
    public static final String EXTRA_SELECTED_INDEX = "extra.selected_index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article_details);
        articles = getIntent().getParcelableArrayListExtra(EXTRA_ARTICLES);
        int selectedIndex = getIntent().getIntExtra(EXTRA_SELECTED_INDEX, 0);

        binding.pager.setAdapter(new ArticleDetailsPagerAdapter(getSupportFragmentManager()));
        binding.pager.setPageTransformer(false, new ParallaxPageTransformer());
        binding.pager.setCurrentItem(selectedIndex);

        setResult(RESULT_OK, getIntent());
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

    public class ParallaxPageTransformer implements ViewPager.PageTransformer {

        public void transformPage(View view, float position) {
            float alpha = 1.0F - Math.abs(position);
            float rotation = 720f * Math.abs(position);
            float translationX = -position * (view.getWidth() / 2);

            view.findViewById(R.id.iv_image).setTranslationX(translationX);

            view.findViewById(R.id.tv_description).setAlpha(alpha);
            view.findViewById(R.id.tv_title_tool_bar).setAlpha(alpha);
            view.findViewById(R.id.tv_author_published_on_date).setAlpha(alpha);
            view.findViewById(R.id.tv_source).setAlpha(alpha);

            view.findViewById(R.id.fab_share).setRotation(rotation);
        }
    }
}

package com.example.tanyayuferova.lifestylenews.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tanyayuferova.lifestylenews.databinding.FragmentArticleDetailsBinding;
import com.example.tanyayuferova.lifestylenews.entity.Article;

/**
 * Created by Tanya Yuferova on 12/17/2017.
 */

public class ArticleDetailsFragment extends Fragment {

    private FragmentArticleDetailsBinding binding;
    public static final String ARGUMENT_ARTICLE = "arg.article";

    public ArticleDetailsFragment() {
    }

    public static ArticleDetailsFragment newInstance(Article article) {
        ArticleDetailsFragment fragment = new ArticleDetailsFragment();
        fragment.setArguments(new Bundle());
        fragment.getArguments().putParcelable(ARGUMENT_ARTICLE, article);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Article article = getArguments().getParcelable(ARGUMENT_ARTICLE);

        binding = FragmentArticleDetailsBinding.inflate(inflater, container, false);
        binding.setArticle(article);
        return binding.getRoot();
    }
}

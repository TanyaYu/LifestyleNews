package com.example.tanyayuferova.lifestylenews.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tanyayuferova.lifestylenews.R;
import com.example.tanyayuferova.lifestylenews.databinding.FragmentArticlesListBinding;
import com.example.tanyayuferova.lifestylenews.entity.Article;
import com.example.tanyayuferova.lifestylenews.sync.ArticlesAsyncTaskLoader;
import com.example.tanyayuferova.lifestylenews.ui.activity.ArticleDetailsActivity;
import com.example.tanyayuferova.lifestylenews.ui.adapter.ArticlesAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Tanya Yuferova on 12/18/2017.
 */

public class ArticlesListFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Article>>,
        ArticlesAdapter.OnClickArticleHandler {

    protected FragmentArticlesListBinding binding;
    protected ArticlesAdapter adapter;
    private LoaderCallback callback;
    private int articlesLimit;
    private Uri uriData;
    protected static int countLoaders = 0;

    public static final String ARGUMENT_URI_DATA = "arg.uri_data";
    public static final String ARGUMENT_ARTICLES_COUNT = "arg.articles_count";
    public static final String ARGUMENT_LOADER_ID = "arg.loader_id";
    public static final int REQUEST_CODE_ARTICLE_DETAILS_ACTIVITY = 12;

    public ArticlesListFragment() {
    }

    public static ArticlesListFragment newInstance(Uri uriData) {
        return newInstance(uriData, 0);
    }

    public static ArticlesListFragment newInstance(Uri uriData, int count) {
        ArticlesListFragment fragment = new ArticlesListFragment();
        fragment.setArguments(new Bundle());
        fragment.getArguments().putParcelable(ARGUMENT_URI_DATA, uriData);
        fragment.getArguments().putInt(ARGUMENT_ARTICLES_COUNT, count);
        fragment.getArguments().putInt(ARGUMENT_LOADER_ID, ++countLoaders);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentArticlesListBinding.inflate(inflater, container, false);

        articlesLimit = getArguments().getInt(ARGUMENT_ARTICLES_COUNT);
        uriData = getArguments().getParcelable(ARGUMENT_URI_DATA);

        int columns = getResources().getInteger(R.integer.articles_list_columns);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), columns);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter = new ArticlesAdapter(this));

        getActivity().getSupportLoaderManager().initLoader(getArguments().getInt(ARGUMENT_LOADER_ID), null, this);

        return binding.getRoot();
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        return new ArticlesAsyncTaskLoader(getContext(), uriData, articlesLimit);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        setAdapterData(data);

        if(callback != null) {
            callback.onLoadFinished(data!=null && data.size()>0);
        }
    }

    protected void setAdapterData(List<Article> data) {
        adapter.setData(new ArrayList<Object>(data));
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {

    }

    @Override
    public void onClickArticle(View view, Article article) {
        List<Article> data = adapter.getArticlesData();
        Intent intent = new Intent(getContext(), ArticleDetailsActivity.class);
        intent.putParcelableArrayListExtra(ArticleDetailsActivity.EXTRA_ARTICLES, new ArrayList<Parcelable>(data));
        intent.putExtra(ArticleDetailsActivity.EXTRA_SELECTED_INDEX, data.indexOf(article));
        startActivityForResult(intent, REQUEST_CODE_ARTICLE_DETAILS_ACTIVITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ARTICLE_DETAILS_ACTIVITY && resultCode == RESULT_OK) {
            if(data != null) {
                List<Article> articles = data.getParcelableArrayListExtra(ArticleDetailsActivity.EXTRA_ARTICLES);
                setAdapterData(articles);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void refreshLoader() {
        getActivity().getSupportLoaderManager().restartLoader(getArguments().getInt(ARGUMENT_LOADER_ID), null, this);
    }

    public void setLoaderCallback(LoaderCallback callback) {
        this.callback = callback;
    }

    public interface LoaderCallback {
        void onLoadFinished(boolean success);
    }
}

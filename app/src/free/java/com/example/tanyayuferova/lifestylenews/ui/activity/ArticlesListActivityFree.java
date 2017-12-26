package com.example.tanyayuferova.lifestylenews.ui.activity;

import com.example.tanyayuferova.lifestylenews.R;
import com.example.tanyayuferova.lifestylenews.ui.fragments.ArticlesListFragmentFree;

/**
 * Created by Tanya Yuferova on 12/26/2017.
 */

public class ArticlesListActivityFree extends ArticlesListActivity {

    @Override
    protected void initArticlesListFragment() {
        articlesListFragment = (ArticlesListFragmentFree) getSupportFragmentManager().findFragmentById(R.id.fragment_list);
        if(articlesListFragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_list, articlesListFragment = ArticlesListFragmentFree.newInstance(uri))
                    .commit();
        }
        articlesListFragment.setLoaderCallback(this);
    }
}

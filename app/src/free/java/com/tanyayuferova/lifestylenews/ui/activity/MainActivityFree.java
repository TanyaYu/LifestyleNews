package com.tanyayuferova.lifestylenews.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import com.tanyayuferova.lifestylenews.R;

/**
 * Created by Tanya Yuferova on 12/26/2017.
 */

public class MainActivityFree extends MainActivity {

    @Override
    protected void startArticlesListActivity(final Uri data, final String title) {
        articlesFavoriteFragment.runRecyclerViewAnimation(R.anim.layout_animation_fall_down);
        articlesRecentFragment.runRecyclerViewAnimation(R.anim.layout_animation_fall_down);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivityFree.this, ArticlesListActivityFree.class);
                intent.setData(data);
                intent.putExtra(ArticlesListActivity.EXTRA_LIST_TITLE, title);
                startActivity(intent);
            }
        }, getResources().getInteger(R.integer.anim_duration_medium));
    }
}

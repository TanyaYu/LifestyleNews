package com.example.tanyayuferova.lifestylenews.ui.activity;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by Tanya Yuferova on 12/26/2017.
 */

public class MainActivityFree extends MainActivity {

    @Override
    protected void startArticlesListActivity(Uri data, String title) {
        Intent intent = new Intent(this, ArticlesListActivityFree.class);
        intent.setData(data);
        intent.putExtra(ArticlesListActivity.EXTRA_LIST_TITLE, title);
        startActivity(intent);
    }
}

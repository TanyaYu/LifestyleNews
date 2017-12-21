package com.example.tanyayuferova.lifestylenews.ui.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.tanyayuferova.lifestylenews.R;
import com.example.tanyayuferova.lifestylenews.databinding.ActivityTopicsBinding;

public class TopicsActivity extends AppCompatActivity {

    private ActivityTopicsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_topics);
    }

    public void onClickButtonOk(View view) {
        finish();
    }
}

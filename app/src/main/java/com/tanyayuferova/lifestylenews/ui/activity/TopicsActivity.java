package com.tanyayuferova.lifestylenews.ui.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tanyayuferova.lifestylenews.R;
import com.tanyayuferova.lifestylenews.databinding.ActivityTopicsBinding;
import com.tanyayuferova.lifestylenews.ui.adapter.TopicsAdapter;
import com.tanyayuferova.lifestylenews.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TopicsActivity extends AppCompatActivity
implements TopicsAdapter.OnClickTopicHandler {

    private ActivityTopicsBinding binding;
    private TopicsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_topics);

        Set<String> topics = PreferencesUtils.getTopicsPreferences(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvTopics.setLayoutManager(layoutManager);
        binding.rvTopics.setHasFixedSize(true);
        binding.rvTopics.setAdapter(adapter = new TopicsAdapter(new ArrayList<String>(topics), this));

        setResult(RESULT_CANCELED);
    }

    public void onNavigationBackClick(View view) {
        finish();
    }

    public void onClickAddTopic(View view) {
        adapter.addItem(binding.etTopic.getText().toString());
        updateTopicsPreferences();

        binding.etTopic.setText("");

        setResult(RESULT_OK);
    }

    @Override
    public void onClickRemoveTopic(View view, String topic) {
        adapter.removeItem(topic);
        updateTopicsPreferences();

        setResult(RESULT_OK);
    }

    protected void updateTopicsPreferences() {
        PreferencesUtils.setTopicsPreferences(this, new HashSet<String>(adapter.getData()));
    }
}

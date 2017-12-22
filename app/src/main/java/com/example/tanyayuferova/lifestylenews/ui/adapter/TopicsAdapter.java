package com.example.tanyayuferova.lifestylenews.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tanyayuferova.lifestylenews.databinding.TopicItemBinding;

import java.util.List;

/**
 * Created by Tanya Yuferova on 12/22/2017.
 */

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.TopicsAdapterViewHolder> {

    public interface OnClickTopicHandler {
        void onClickRemoveTopic(View view, String topic);
    }

    private List<String> data;
    private OnClickTopicHandler clickHandler;

    public TopicsAdapter() {
    }

    public TopicsAdapter(List<String> data, OnClickTopicHandler clickHandler) {
        this.data = data;
        this.clickHandler = clickHandler;
    }

    public class TopicsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TopicItemBinding binding;

        public TopicsAdapterViewHolder(TopicItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String item) {
            binding.setTopic(item);
            binding.fabRemoveTopic.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickHandler.onClickRemoveTopic(binding.getRoot(), binding.getTopic());
        }
    }

    @Override
    public TopicsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TopicItemBinding itemBinding = TopicItemBinding.inflate(layoutInflater, parent, false);
        return new TopicsAdapterViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(TopicsAdapterViewHolder holder, int position) {
        String item = data.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void removeItem(String topic) {
        if (data.contains(topic)) {
            data.remove(topic);
            notifyDataSetChanged();
        }
    }

    public void addItem(String topic) {
        if (!data.contains(topic)) {
            data.add(topic);
            notifyDataSetChanged();
        }
    }

    public List<String> getData() {
        return data;
    }
}

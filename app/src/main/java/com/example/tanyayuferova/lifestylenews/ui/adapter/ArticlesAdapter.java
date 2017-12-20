package com.example.tanyayuferova.lifestylenews.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tanyayuferova.lifestylenews.databinding.ArticleItemBinding;
import com.example.tanyayuferova.lifestylenews.entity.Article;

import java.util.List;

/**
 * Adapter for displaying articles
 * Created by Tanya Yuferova on 12/17/2017.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesAdapterViewHolder>  {

    public interface OnClickArticleHandler {
        void onClickArticle(View view, Article article);
    }

    private List<Article> data;
    private OnClickArticleHandler clickHandler;

    public ArticlesAdapter() {
    }

    public ArticlesAdapter(OnClickArticleHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    public void setClickHandler(OnClickArticleHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    public class ArticlesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ArticleItemBinding binding;

        public ArticlesAdapterViewHolder(ArticleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Article item) {
            binding.setArticle(item);
            binding.getRoot().setOnClickListener(this);
            binding.tvSourcePublishedOnDate.setMovementMethod(LinkMovementMethod.getInstance());
        }

        @Override
        public void onClick(View v) {
            clickHandler.onClickArticle(binding.getRoot(), binding.getArticle());
        }
    }

    @Override
    public ArticlesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ArticleItemBinding itemBinding = ArticleItemBinding.inflate(layoutInflater, parent, false);
        return new ArticlesAdapterViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(ArticlesAdapterViewHolder holder, int position) {
        Article item = data.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return data==null ? 0 : data.size();
    }

    public void setData(List<Article> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<Article> getData() {
        return data;
    }
}

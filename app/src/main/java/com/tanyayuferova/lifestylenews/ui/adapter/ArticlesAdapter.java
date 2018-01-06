package com.tanyayuferova.lifestylenews.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tanyayuferova.lifestylenews.databinding.ArticleItemBinding;
import com.tanyayuferova.lifestylenews.entity.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for displaying articles
 * Created by Tanya Yuferova on 12/17/2017.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public interface OnClickArticleHandler {
        void onClickArticle(View view, Article article);
    }

    // Object - because we can store Articles or NativeExpressAdView in data
    protected List<Object> data;
    private OnClickArticleHandler clickHandler;

    public ArticlesAdapter() {
    }

    public ArticlesAdapter(OnClickArticleHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    public class ArticlesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ArticleItemBinding binding;

        public ArticlesAdapterViewHolder(ArticleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Article item) {
            binding.setContext(binding.getRoot().getContext());
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ArticleItemBinding itemBinding = ArticleItemBinding.inflate(layoutInflater, parent, false);
        return new ArticlesAdapterViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Article item = (Article) data.get(position);
        ((ArticlesAdapterViewHolder) holder).bind(item);
    }

    @Override
    public int getItemCount() {
        return data==null ? 0 : data.size();
    }

    public void setData(List<Object> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<Object> getData() {
        return data;
    }

    /**
     * Returns data list including only articles
     * @return
     */
    public List<Article> getArticlesData() {
        List<Article> result = new ArrayList<>();
        for(Object o : data) {
            if(o instanceof Article)
                result.add((Article) o);
        }
        return result;
    }
}

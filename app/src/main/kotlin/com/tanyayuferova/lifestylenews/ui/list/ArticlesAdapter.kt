package com.tanyayuferova.lifestylenews.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tanyayuferova.lifestylenews.databinding.ItemArticleBinding

/**
 * Author: Tanya Yuferova
 * Date: 7/29/19
 */
class ArticlesAdapter(
    private val actionsHandler: ActionsHandler
) : ListAdapter<ArticleListItem, ArticlesAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<ArticleListItem>() {
        override fun areItemsTheSame(old: ArticleListItem, new: ArticleListItem): Boolean {
            return old.id == new.id
        }

        override fun areContentsTheSame(old: ArticleListItem, new: ArticleListItem): Boolean {
            return old == new
        }

        override fun getChangePayload(old: ArticleListItem, new: ArticleListItem): Any? {
            return if (old.isFavorite != new.isFavorite)
                IS_FAVORITE_PAYLOAD
            else super.getChangePayload(old, new)
        }
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemArticleBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains(IS_FAVORITE_PAYLOAD)) {
            holder.bindFavorite(getItem(position).isFavorite)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    inner class ViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArticleListItem) {
            binding.item = item
            binding.handler = actionsHandler
            bindFavorite(item.isFavorite)
        }

        fun bindFavorite(isFavorite: Boolean) {
            binding.isFavorite = isFavorite
        }
    }

    interface ActionsHandler {
        fun onArticleClick(id: Int)
        fun onFavoriteClick(id: Int, isFavorite: Boolean)
        fun onReadClick(id: Int)
    }

    companion object {
        const val IS_FAVORITE_PAYLOAD = "IS_FAVORITE_PAYLOAD"
    }
}
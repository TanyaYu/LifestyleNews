package com.tanyayuferova.lifestylenews.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.tanyayuferova.lifestylenews.R
import com.tanyayuferova.lifestylenews.ui.common.PaginationWrapperAdapter.Footer.Loader
import com.tanyayuferova.lifestylenews.ui.common.PaginationWrapperAdapter.Footer.Error

/**
 * Author: Tanya Yuferova
 * Date: 7/27/2019
 */
class PaginationWrapperAdapter<T, VH : RecyclerView.ViewHolder>(
    private val pageSize: Int,
    private val newPageRequest: () -> Unit,
    private val adapter: ListAdapter<T, VH>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var footer: Footer? = null
        set(new) {
            if (field != new) {
                val old = field
                field = new
                when {
                    old != null && new == null -> notifyItemRemoved(adapter.itemCount)
                    old == null && new != null -> notifyItemInserted(adapter.itemCount)
                    old != null && new != null -> notifyItemChanged(adapter.itemCount)
                }
            }
        }

    init {
        adapter.registerAdapterDataObserver(Observer(this))
    }

    fun submitList(list: List<T>?) {
        adapter.submitList(list)
    }

    override fun getItemCount(): Int {
        return adapter.itemCount + if (hasFooter) 1 else 0
    }

    private fun isFooter(position: Int) = hasFooter && position == adapter.itemCount

    private fun isFooterError(position: Int) = isFooter(position) && footer is Error

    private fun isFooterLoader(position: Int) = isFooter(position) && footer is Loader

    private val hasFooter: Boolean
        get() = footer != null

    override fun getItemViewType(position: Int): Int {
        return when {
            isFooterLoader(position) -> LOADER_FOOTER_TYPE
            isFooterError(position) -> ERROR_FOOTER_TYPE
            else -> adapter.getItemViewType(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            LOADER_FOOTER_TYPE -> {
                LoaderViewHolder(
                    inflater.inflate(
                        R.layout.item_footer_loader,
                        parent,
                        false
                    )
                )
            }
            ERROR_FOOTER_TYPE -> {
                ErrorViewHolder(
                    inflater.inflate(
                        R.layout.item_footer_error,
                        parent,
                        false
                    )
                )
            }
            else -> adapter.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bindViewHolder(holder, position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        bindViewHolder(holder, position, payloads)
    }

    @Suppress("UNCHECKED_CAST")
    private fun bindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any> = mutableListOf()
    ) {
        when {
            !isFooter(position) -> {
                adapter.onBindViewHolder(holder as VH, position, payloads)
            }
            isFooterError(position) -> {
                bindFooter(holder as ErrorViewHolder, footer as Error)
            }
        }

        if (position > itemCount - pageSize / 2) {
            newPageRequest()
        }
    }

    private fun bindFooter(holder: ErrorViewHolder, footer: Error) = with(holder) {
        messageView.text = footer.errorMessage
        retryButton.setOnClickListener {
            footer.onRetry()
        }
    }

    private class Observer(private val adapter: RecyclerView.Adapter<*>) : RecyclerView.AdapterDataObserver() {

        override fun onChanged() {
            adapter.notifyDataSetChanged()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            adapter.notifyItemRangeRemoved(positionStart, itemCount)
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            adapter.notifyItemMoved(fromPosition, toPosition)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            adapter.notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            adapter.notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(positionStart, itemCount, payload)
        }
    }

    class LoaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class ErrorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageView: TextView = view.findViewById(R.id.message)
        val retryButton: MaterialButton = view.findViewById(R.id.retryButton)
    }

    sealed class Footer {
        object Loader : Footer()
        class Error(val errorMessage: String, val onRetry: () -> Unit) : Footer()
    }

    companion object {
        const val LOADER_FOOTER_TYPE = -1
        const val ERROR_FOOTER_TYPE = -2
    }
}
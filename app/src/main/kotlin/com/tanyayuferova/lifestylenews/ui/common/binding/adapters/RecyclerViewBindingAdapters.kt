package com.tanyayuferova.lifestylenews.ui.common.binding.adapters

import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tanyayuferova.lifestylenews.ui.common.ItemDivider
import com.tanyayuferova.lifestylenews.ui.common.PaginationWrapperAdapter

/**
 * Author: Tanya Yuferova
 * Date: 8/1/2019
 */

@BindingAdapter("listData")
fun <T> RecyclerView.setListData(data: List<T>?) {
    if (adapter is ListAdapter<*, *>)
        (adapter as? ListAdapter<T, *>)?.submitList(data)
    if (adapter is PaginationWrapperAdapter<*, *>)
        (adapter as? PaginationWrapperAdapter<T, *>)?.submitList(data)
}

@BindingAdapter("hasFixedSize")
fun RecyclerView.hasFixedSize(hasFixedSize: Boolean) {
    setHasFixedSize(hasFixedSize)
}

@BindingAdapter("adapterFooter")
fun <T> RecyclerView.setAdapterFooter(footer: PaginationWrapperAdapter.Footer?) {
    val adapter = adapter
    if (adapter is PaginationWrapperAdapter<*, *>) {
        adapter.footer = footer
    }
}

@BindingAdapter(value = ["itemDecoration", "android:orientation"], requireAll = false)
fun RecyclerView.addItemDecoration(drawable: Drawable, recyclerOrientation: Int?) {
    val orientation = recyclerOrientation
        ?: (layoutManager as? LinearLayoutManager)?.orientation
        ?: LinearLayoutManager.VERTICAL
    this.addItemDecoration(
        ItemDivider(
            context,
            orientation,
            drawable
        )
    )
}
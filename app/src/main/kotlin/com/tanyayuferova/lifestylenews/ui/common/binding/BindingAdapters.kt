package com.tanyayuferova.lifestylenews.ui.common.binding

import android.graphics.drawable.Drawable
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.tanyayuferova.lifestylenews.ui.common.ItemDivider
import com.tanyayuferova.lifestylenews.ui.common.PaginationWrapperAdapter

/**
 * Author: Tanya Yuferova
 * Date: 7/26/19
 */
@BindingAdapter("onRefreshListener")
fun SwipeRefreshLayout.onRefreshListener(action: (() -> Unit)?) {
    setOnRefreshListener {
        action?.invoke()
    }
}

@BindingAdapter("isRefreshing")
fun SwipeRefreshLayout.isRefreshing(isRefreshing: Boolean) {
    this.isRefreshing = isRefreshing
}

@BindingAdapter("listData")
fun <T>RecyclerView.listData(data: List<T>?) {
    if(adapter is ListAdapter<*, *>)
        (adapter as? ListAdapter<T, *>)?.submitList(data)
    if(adapter is PaginationWrapperAdapter<*, *>)
        (adapter as? PaginationWrapperAdapter<T, *>)?.submitList(data)
}

@BindingAdapter("hasFixedSize")
fun RecyclerView.hasFixedSize(hasFixedSize: Boolean) {
    setHasFixedSize(hasFixedSize)
}

@BindingAdapter("adapterFooter")
fun <T>RecyclerView.adapterFooter(footer: PaginationWrapperAdapter.Footer?) {
    val adapter = adapter
    if(adapter is PaginationWrapperAdapter<*, *>) {
        adapter.footer = footer
    }
}

@BindingAdapter("isVisible")
fun View.isVisible(isVisible: Boolean) {
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .into(this)
}

@BindingAdapter("imageUrl", "error")
fun ImageView.loadImageError(url: String?, error: Drawable) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .placeholder(error)
        .error(error)
        .into(this)
}

@BindingAdapter("onNavigationIconClick")
fun Toolbar.onNavigationIconClick(action: () -> Unit) {
    setNavigationOnClickListener { action() }
}

@BindingAdapter("linkMovement")
fun TextView.setLinkMovement(linkMovement: Boolean) {
    if(linkMovement) {
        movementMethod = LinkMovementMethod.getInstance()
    }
}

@BindingAdapter("isSelected")
fun View.isSelected(isSelected: Boolean) {
    this.isSelected = isSelected
}

@BindingAdapter(value = ["itemDecoration", "android:orientation"], requireAll = false)
fun RecyclerView.itemDecoration(drawable: Drawable, recyclerOrientation: Int?) {
    val orientation = recyclerOrientation
        ?:(layoutManager as? LinearLayoutManager)?.orientation
        ?: VERTICAL
    this.addItemDecoration(
        ItemDivider(
            context,
            orientation,
            drawable
        )
    )
}


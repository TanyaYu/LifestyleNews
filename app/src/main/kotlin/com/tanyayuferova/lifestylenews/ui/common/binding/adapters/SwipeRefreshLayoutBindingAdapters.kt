package com.tanyayuferova.lifestylenews.ui.common.binding.adapters

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * Author: Tanya Yuferova
 * Date: 8/1/2019
 */

@BindingAdapter("onRefreshListener")
fun SwipeRefreshLayout.setOnRefreshListener(action: (() -> Unit)?) {
    setOnRefreshListener {
        action?.invoke()
    }
}

@BindingAdapter("isRefreshing")
fun SwipeRefreshLayout.isRefreshing(isRefreshing: Boolean) {
    this.isRefreshing = isRefreshing
}
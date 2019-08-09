package com.tanyayuferova.lifestylenews.ui.common.binding.adapters

import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter

/**
 * Author: Tanya Yuferova
 * Date: 8/1/2019
 */

@BindingAdapter("onNavigationIconClick")
fun Toolbar.setONavigationIconClick(action: () -> Unit) {
    setNavigationOnClickListener { action() }
}

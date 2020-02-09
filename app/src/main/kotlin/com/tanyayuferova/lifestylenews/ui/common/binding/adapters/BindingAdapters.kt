package com.tanyayuferova.lifestylenews.ui.common.binding.adapters

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.tanyayuferova.lifestylenews.ui.common.binding.mediators.ViewMediator

/**
 * Author: Tanya Yuferova
 * Date: 7/26/19
 */

@BindingAdapter("isVisible")
fun View.isVisible(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("isSelected")
fun View.isSelected(isSelected: Boolean) {
    this.isSelected = isSelected
}

@BindingAdapter("mediator")
fun <T : View> T.setViewMediator(mediator: ViewMediator<T>) {
    mediator.setUp(this)
}
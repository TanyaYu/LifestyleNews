package com.tanyayuferova.lifestylenews.ui.common.binding

import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * Author: Tanya Yuferova
 * Date: 7/26/19
 */

@BindingAdapter("isVisible")
fun View.isVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("isSelected")
fun View.isSelected(isSelected: Boolean) {
    this.isSelected = isSelected
}

@BindingAdapter("linkMovement")
fun TextView.setLinkMovement(linkMovement: Boolean) {
    if (linkMovement) {
        movementMethod = LinkMovementMethod.getInstance()
    }
}

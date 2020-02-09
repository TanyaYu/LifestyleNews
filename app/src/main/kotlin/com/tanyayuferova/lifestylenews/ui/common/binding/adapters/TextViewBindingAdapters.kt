package com.tanyayuferova.lifestylenews.ui.common.binding.adapters

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * Author: Tanya Yuferova
 * Date: 8/9/19
 */
@BindingAdapter("linkMovement")
fun TextView.setLinkMovement(linkMovement: Boolean) {
    if (linkMovement) {
        movementMethod = LinkMovementMethod.getInstance()
    }
}
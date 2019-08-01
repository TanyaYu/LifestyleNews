package com.tanyayuferova.lifestylenews.ui.common.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Author: Tanya Yuferova
 * Date: 8/1/2019
 */

@BindingAdapter(value = ["imageUrl", "error", "placeholder"], requireAll = false)
fun ImageView.loadImageError(url: String?, error: Drawable?, placeholder: Drawable?) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .placeholder(placeholder)
        .error(error)
        .into(this)
}
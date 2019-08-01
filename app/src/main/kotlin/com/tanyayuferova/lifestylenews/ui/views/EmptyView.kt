package com.tanyayuferova.lifestylenews.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.tanyayuferova.lifestylenews.R
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter


/**
 * Author: Tanya Yuferova
 * Date: 8/1/2019
 */
class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val textView: TextView
    private val iconView: ImageView
    private val actionButton: MaterialButton

    var text: CharSequence?
        get() = textView.text
        set(value) {
            textView.text = value
        }

    var icon: Drawable?
        get() = iconView.drawable
        set(value) {
            iconView.setImageDrawable(value)
            iconView.isInvisible = value == null
        }

    var isActionEnabled: Boolean
        get() = actionButton.isVisible
        set(value) {
            actionButton.isInvisible = !value
        }

    init {
        inflate(context, R.layout.view_empty, this)

        textView = findViewById(R.id.text)
        iconView = findViewById(R.id.icon)
        actionButton = findViewById(R.id.action)

        orientation = VERTICAL
        gravity = Gravity.CENTER_HORIZONTAL

        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.EmptyView,
            defStyleAttr, 0
        )
        text = typedArray.getText(R.styleable.EmptyView_text)
        icon = typedArray.getDrawable(R.styleable.EmptyView_icon)
        val tint = typedArray.getColor(R.styleable.EmptyView_icon_tint, ContextCompat.getColor(context, R.color.white))
        icon?.let { DrawableCompat.setTint(it, tint) }
        actionButton.text = typedArray.getText(R.styleable.EmptyView_action)
        isActionEnabled = typedArray.getBoolean(R.styleable.EmptyView_action_enabled, false)
        typedArray.recycle()
    }

    fun setAction(action: () -> Unit) {
        actionButton.setOnClickListener { action() }
    }

    companion object {

        @JvmStatic
        @BindingAdapter("onActionClick")
        fun EmptyView.setOnActionClick(action: () -> Unit) {
            setAction(action)
        }
    }
}
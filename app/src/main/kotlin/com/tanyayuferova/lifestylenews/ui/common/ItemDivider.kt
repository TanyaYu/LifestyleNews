package com.tanyayuferova.lifestylenews.ui.common

import androidx.recyclerview.widget.RecyclerView

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanyayuferova.lifestylenews.R
import kotlin.math.roundToInt

open class ItemDivider(
    context: Context,
    private val orientation: Int = VERTICAL,
    private val divider: Drawable = ContextCompat.getDrawable(context, R.drawable.divider)
        ?: throw Resources.NotFoundException("Can't find default divider drawable R.drawable.divider")
) : RecyclerView.ItemDecoration() {

    private val bounds = Rect()

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager == null) {
            return
        }
        if (orientation == VERTICAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    protected open fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left = 0
        val right = parent.width

        val childCount = parent.childCount
        for (i in 0 until childCount - 2) {
            val child = parent.getChildAt(i)
            parent.layoutManager?.getDecoratedBoundsWithMargins(child, bounds)
            val bottom = bounds.bottom + child.translationY.roundToInt()
            val top = bottom - divider.intrinsicHeight
            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }
        canvas.restore()
    }

    protected open fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val top = 0
        val bottom = parent.height

        val childCount = parent.childCount
        for (i in 0 until childCount - 2) {
            val child = parent.getChildAt(i)
            parent.layoutManager?.getDecoratedBoundsWithMargins(child, bounds)
            val right = bounds.right + child.translationX.roundToInt()
            val left = right - divider.intrinsicWidth
            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }
        canvas.restore()
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }

        val itemCount = state.itemCount

        if (itemPosition < itemCount - 1) {
            if (orientation == VERTICAL) {
                outRect.set(0, 0, 0, divider.intrinsicHeight)
            } else {
                outRect.set(0, 0, divider.intrinsicWidth, 0)
            }
        } else {
            outRect.set(0, 0, 0, 0)
        }
    }

    companion object {
        const val HORIZONTAL = LinearLayoutManager.HORIZONTAL
        const val VERTICAL = LinearLayoutManager.VERTICAL
    }
}

package com.tanyayuferova.lifestylenews.ui.common.binding.mediators

import android.view.View

/**
 * Author: Tanya Yuferova
 * Date: 8/9/19
 */
open class ViewMediator<V : View> {

    protected var view: V? = null

    fun setUp(view: V) {
        this.view = view
    }

    fun clear() {
        view = null
    }
}
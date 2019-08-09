package com.tanyayuferova.lifestylenews.ui.common.binding.mediators

/**
 * Author: Tanya Yuferova
 * Date: 8/9/19
 */
interface WebViewInteraction {
    fun goBack()
    fun goForward()
    fun canGoBack(): Boolean?
    fun canGoForward(): Boolean?
}
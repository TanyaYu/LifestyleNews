package com.tanyayuferova.lifestylenews.ui.common.binding.mediators

import android.webkit.WebView

/**
 * Author: Tanya Yuferova
 * Date: 8/9/19
 */
class WebViewMediator : ViewMediator<WebView>(), WebViewInteraction {

    override fun goBack() {
        view?.goBack()
    }

    override fun goForward() {
        view?.goForward()
    }

    override fun canGoBack(): Boolean? {
        return view?.canGoBack()
    }

    override fun canGoForward(): Boolean? {
        return view?.canGoForward()
    }
}
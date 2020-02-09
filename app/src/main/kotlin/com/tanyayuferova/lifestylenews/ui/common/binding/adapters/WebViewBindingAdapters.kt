package com.tanyayuferova.lifestylenews.ui.common.binding.adapters

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.BindingAdapter

/**
 * Author: Tanya Yuferova
 * Date: 8/7/19
 */

@BindingAdapter("url")
fun WebView.setUrl(url: String) {
    loadUrl(url)
}

@BindingAdapter("webViewClient")
fun WebView.bindWebViewClient(client: WebViewClient) {
    webViewClient = client
}

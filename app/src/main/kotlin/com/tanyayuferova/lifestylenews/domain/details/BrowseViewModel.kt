package com.tanyayuferova.lifestylenews.domain.details

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.ObservableBoolean
import androidx.navigation.NavController
import com.tanyayuferova.lifestylenews.domain.baseviewmodel.ViewModel
import com.tanyayuferova.lifestylenews.domain.common.ActivityIntentsExecutor
import com.tanyayuferova.lifestylenews.ui.common.binding.mediators.WebViewMediator
import javax.inject.Inject

/**
 * Author: Tanya Yuferova
 * Date: 8/7/19
 */
class BrowseViewModel @Inject constructor(
    private val navController: NavController,
    private val activityIntentsExecutor: ActivityIntentsExecutor
) : ViewModel() {

    private var uri: Uri = Uri.EMPTY

    var url: String
        get() = uri.toString()
        set(value) {
            uri = Uri.parse(value)
        }

    val isLoading = ObservableBoolean(true)

    val canGoBack = ObservableBoolean(false)
    val canGoForward = ObservableBoolean(false)

    val webViewMediator = WebViewMediator()

    val webClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this@BrowseViewModel.uri = request.url
            }
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            isLoading.set(true)
            webViewMediator.canGoBack()?.let(canGoBack::set)
            webViewMediator.canGoForward()?.let(canGoForward::set)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            isLoading.set(false)
        }
    }

    fun onBackClick() {
        webViewMediator.goBack()
    }

    fun onForwardClick() {
        webViewMediator.goForward()
    }

    fun onCloseClick() {
        navController.navigateUp()
    }

    fun onViewInBrowserClick() {
        activityIntentsExecutor.view(uri)
    }
}
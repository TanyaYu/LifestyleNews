package com.tanyayuferova.lifestylenews.data.network

import com.tanyayuferova.lifestylenews.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Author: Tanya Yuferova
 * Date: 7/31/19
 */
class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val newHttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("apiKey", BuildConfig.API_KEY)
            .build()

        val newRequest = original.newBuilder().url(newHttpUrl).build()

        return chain.proceed(newRequest)
    }
}
package com.tanyayuferova.lifestylenews.data.network

import com.tanyayuferova.lifestylenews.data.network.exeption.*
import com.tanyayuferova.lifestylenews.data.network.status.NetworkStatusService
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Author: Tanya Yuferova
 * Date: 8/7/19
 */
class ErrorInterceptor(
    private val networkStatusService: NetworkStatusService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (!networkStatusService.isNetworkAvailable())
            throw NoConnectionException()

        when (response.code()) {
            400 -> throw BadRequestException()
            401 -> throw UnauthorizedCallException()
            426 -> throw UpgradeRequiredException()
            429 -> throw TooManyRequestsException()
            in 500..600 -> throw ServerError()
        }

        return response
    }
}
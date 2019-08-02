package com.tanyayuferova.lifestylenews.data.network.status

import io.reactivex.Observable

/**
 * Author: Tanya Yuferova
 * Date: 8/2/19
 */
interface NetworkStatusService {
    fun observe(): Observable<Boolean>
    fun isNetworkAvailable(): Boolean
}
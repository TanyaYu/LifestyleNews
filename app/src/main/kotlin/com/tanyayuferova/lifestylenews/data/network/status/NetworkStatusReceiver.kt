package com.tanyayuferova.lifestylenews.data.network.status

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Author: Tanya Yuferova
 * Date: 8/2/19
 */
class NetworkStatusReceiver(
    private val context: Context
) : BroadcastReceiver(), NetworkStatusService {

    private val networkInfoSubject = BehaviorSubject.create<Boolean>()

    override fun onReceive(context: Context, intent: Intent?) {
        networkInfoSubject.onNext(context.isConnected())
    }

    override fun observe(): Observable<Boolean> {
        return networkInfoSubject.hide()
    }

    override fun isNetworkAvailable(): Boolean {
        return networkInfoSubject.value ?: context.isConnected()
    }

    private fun Context.isConnected() =
        (applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetworkInfo?.isConnected == true
}
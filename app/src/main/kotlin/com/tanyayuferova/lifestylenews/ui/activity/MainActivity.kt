package com.tanyayuferova.lifestylenews.ui.activity

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import com.tanyayuferova.lifestylenews.R
import com.tanyayuferova.lifestylenews.data.network.status.NetworkStatusReceiver
import com.tanyayuferova.lifestylenews.domain.activityCommand.ActivityHolder
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var networkStatusReceiver: NetworkStatusReceiver

    @Inject
    lateinit var activityHolder: ActivityHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        activityHolder.setActivity(this)
        registerReceiver(
            networkStatusReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onPause() {
        activityHolder.setActivity(null)
        unregisterReceiver(networkStatusReceiver)
        super.onPause()
    }
}

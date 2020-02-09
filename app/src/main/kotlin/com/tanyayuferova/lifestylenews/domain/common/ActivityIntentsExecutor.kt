package com.tanyayuferova.lifestylenews.domain.common

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import com.tanyayuferova.lifestylenews.domain.activityCommand.ActivityCommand
import com.tanyayuferova.lifestylenews.domain.activityCommand.ActivityCommandsExecutor
import javax.inject.Inject

/**
 * Author: Tanya Yuferova
 * Date: 8/7/19
 */
class ActivityIntentsExecutor @Inject constructor(
    private val activityCommandsExecutor: ActivityCommandsExecutor
) {

    fun view(url: String) {
        view(Uri.parse(url))
    }

    fun view(uri: Uri) {
        val command = StartActivityCommand(ACTION_VIEW, uri)
        activityCommandsExecutor.execute(command)
    }

    class StartActivityCommand(
        val action: String,
        val data: Uri
    ) : ActivityCommand {
        override fun execute(activity: Activity) {
            activity.startActivity(Intent(action, data))
        }
    }
}
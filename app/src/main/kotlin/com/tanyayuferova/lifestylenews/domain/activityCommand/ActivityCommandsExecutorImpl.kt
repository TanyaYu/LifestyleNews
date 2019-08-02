package com.tanyayuferova.lifestylenews.domain.activityCommand

import android.app.Activity

/**
 * Author: Tanya Yuferova
 * Date: 8/2/19
 */

class ActivityCommandsExecutorImpl : ActivityCommandsExecutor {

    private var activity: Activity? = null

    override fun setActivity(activity: Activity?) {
        this.activity = activity
    }

    override fun execute(command: ActivityCommand) {
        activity?.let(command::execute)
        if(activity == null) {
            // todo put in command stack
        }
    }
}
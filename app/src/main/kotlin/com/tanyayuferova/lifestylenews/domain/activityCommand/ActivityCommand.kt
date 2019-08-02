package com.tanyayuferova.lifestylenews.domain.activityCommand

import android.app.Activity

/**
 * Author: Tanya Yuferova
 * Date: 8/2/19
 */
interface ActivityCommand {
    fun execute(activity: Activity)
}
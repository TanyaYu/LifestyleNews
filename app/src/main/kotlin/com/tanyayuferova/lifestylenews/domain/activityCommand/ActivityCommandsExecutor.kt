package com.tanyayuferova.lifestylenews.domain.activityCommand

/**
 * Author: Tanya Yuferova
 * Date: 8/2/19
 */
interface ActivityCommandsExecutor : ActivityHolder {
    fun execute(command: ActivityCommand)
}
package com.tanyayuferova.lifestylenews.domain.common

import android.app.Activity
import android.content.res.Resources
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.tanyayuferova.lifestylenews.R
import com.tanyayuferova.lifestylenews.domain.activityCommand.ActivityCommand
import com.tanyayuferova.lifestylenews.domain.activityCommand.ActivityCommandsExecutor
import javax.inject.Inject

/**
 * Author: Tanya Yuferova
 * Date: 8/2/19
 */
class SnackBarController @Inject constructor(
    private val activityCommandsExecutor: ActivityCommandsExecutor,
    private val res: Resources
) {

    fun long(message: String, builder: SnackBarBuilder.() -> Unit = {}) {
        val command = ShowSnackBarActivityCommand(Snackbar.LENGTH_LONG, message, builder)
        activityCommandsExecutor.execute(command)
    }

    fun long(@StringRes messageId: Int, builder: SnackBarBuilder.() -> Unit = {}) {
        val command = ShowSnackBarActivityCommand(
            Snackbar.LENGTH_LONG,
            res.getString(messageId),
            builder
        )
        activityCommandsExecutor.execute(command)
    }

    fun short(message: String, builder: SnackBarBuilder.() -> Unit = {}) {
        val command = ShowSnackBarActivityCommand(Snackbar.LENGTH_SHORT, message, builder)
        activityCommandsExecutor.execute(command)
    }

    fun short(@StringRes messageId: Int, builder: SnackBarBuilder.() -> Unit = {}) {
        val command = ShowSnackBarActivityCommand(
            Snackbar.LENGTH_SHORT,
            res.getString(messageId),
            builder
        )
        activityCommandsExecutor.execute(command)
    }

    class ShowSnackBarActivityCommand(
        private val length: Int,
        private val text: String,
        private val builder: SnackBarBuilder.() -> Unit
    ) : ActivityCommand {

        override fun execute(activity: Activity) {
            val view = activity.findViewById<View>(R.id.container)
            Snackbar.make(view, text, length)
                .apply {
                    SnackBarBuilderImpl(this).apply(builder)
                }
                .show()
        }
    }

    private class SnackBarBuilderImpl(private val snackBar: Snackbar) : SnackBarBuilder {

        override fun setAction(titleRes: Int, action: () -> Unit) {
            snackBar.setAction(titleRes) { action() }
        }

        override fun setAction(title: String, action: () -> Unit) {
            snackBar.setAction(title) { action() }
        }
    }

    interface SnackBarBuilder {
        fun setAction(@StringRes titleRes: Int, action: () -> Unit)
        fun setAction(title: String, action: () -> Unit)
    }
}
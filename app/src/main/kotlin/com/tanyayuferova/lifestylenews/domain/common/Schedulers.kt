package com.tanyayuferova.lifestylenews.domain.common

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Author: Tanya Yuferova
 * Date: 7/27/2019
 */
object Schedulers {
    val main = AndroidSchedulers.mainThread()
    val computation = Schedulers.computation()
    val io = Schedulers.io()
}


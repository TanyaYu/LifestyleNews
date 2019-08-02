package com.tanyayuferova.lifestylenews.domain.baseviewmodel

import androidx.annotation.CallSuper
import com.tanyayuferova.lifestylenews.domain.common.Schedulers.computation
import com.tanyayuferova.lifestylenews.domain.common.Schedulers.main
import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

/**
 * Author: Tanya Yuferova
 * Date: 7/27/2019
 */
open class RxViewModel : ViewModel() {

    private val disposable = CompositeDisposable()

    fun <T : Any> Flowable<T>.bindSubscribeBy(
        onNext: (T) -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (Throwable) -> Unit = Timber::e,
        subscribeOn: Scheduler = computation,
        observeOn: Scheduler = main
    ) {
        disposable += this
            .subscribeOn(subscribeOn)
            .observeOn(observeOn)
            .subscribeBy(
                onNext = onNext,
                onComplete= onComplete,
                onError = onError
            )
    }

    fun <T : Any> Observable<T>.bindSubscribeBy(
        onNext: (T) -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (Throwable) -> Unit = Timber::e,
        subscribeOn: Scheduler = computation,
        observeOn: Scheduler = main
    ) {
        disposable += this
            .subscribeOn(subscribeOn)
            .observeOn(observeOn)
            .subscribeBy(
                onNext = onNext,
                onComplete= onComplete,
                onError = onError
            )
    }

    fun <T : Any> Single<T>.bindSubscribeBy(
        onSuccess: (T) -> Unit = {},
        onError: (Throwable) -> Unit = Timber::e,
        subscribeOn: Scheduler = computation,
        observeOn: Scheduler = main
    ) {
        disposable += this
            .subscribeOn(subscribeOn)
            .observeOn(observeOn)
            .subscribeBy(
                onSuccess = onSuccess,
                onError = onError
            )
    }

    fun Completable.bindSubscribeBy(
        onComplete: () -> Unit = {},
        onError: (Throwable) -> Unit = Timber::e,
        subscribeOn: Scheduler = computation,
        observeOn: Scheduler = main
    ) {
        disposable += this
            .subscribeOn(subscribeOn)
            .observeOn(observeOn)
            .subscribeBy(
                onComplete = onComplete,
                onError = onError
            )
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}
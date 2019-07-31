package com.tanyayuferova.lifestylenews.utils

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction

/**
 * Author: Tanya Yuferova
 * Date: 7/27/2019
 */
fun <X: Any, Y: Any>Single<List<X>>.mapList(transformer: (X) -> Y) : Single<List<Y>> {
    return this.map { it.map(transformer) }
}
fun <X: Any, Y: Any>Flowable<List<X>>.mapList(transformer: (X) -> Y) : Flowable<List<Y>> {
    return this.map { it.map(transformer) }
}
fun <X: Any, Y: Any>Observable<List<X>>.mapList(transformer: (X) -> Y) : Observable<List<Y>> {
    return this.map { it.map(transformer) }
}
fun <X, Y, Z>Observable<X>.combineLatest(observable: Observable<Y>, combiner: (X, Y) -> Z): Observable<Z> {
    return Observable.combineLatest(this, observable, BiFunction(combiner))
}
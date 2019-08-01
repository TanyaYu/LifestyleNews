package com.tanyayuferova.lifestylenews.domain.main

import android.content.res.Resources
import androidx.databinding.ObservableField
import com.tanyayuferova.lifestylenews.data.articles.ArticlesRepository
import com.tanyayuferova.lifestylenews.data.articles.combineWithFavorites
import com.tanyayuferova.lifestylenews.domain.common.Pagination
import com.tanyayuferova.lifestylenews.domain.baseviewmodel.RxViewModel
import com.tanyayuferova.lifestylenews.domain.common.Schedulers.main
import com.tanyayuferova.lifestylenews.ui.list.ArticleListItem
import com.tanyayuferova.lifestylenews.domain.main.ListViewModel.DataState.*
import com.tanyayuferova.lifestylenews.ui.common.PaginationWrapperAdapter.Footer
import com.tanyayuferova.lifestylenews.utils.mapList
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

/**
 * Author: Tanya Yuferova
 * Date: 7/26/19
 */
class ListViewModel @Inject constructor(
    private val res: Resources,
    private val articlesRepository: ArticlesRepository
) : RxViewModel(), Pagination.ViewInteraction<ArticleListItem> {

    val articles = ObservableField<List<ArticleListItem>>()
    val state = ObservableField(LOADING)
    val footer = ObservableField<Footer?>()

    private val dataSubject = BehaviorSubject.create<List<ArticleListItem>>()
    private val pagination = Pagination(
        dataProvider = ::requestPage,
        viewInteraction = this
    )

    init {
        articlesRepository.clearCache()
            .bindSubscribeBy(pagination::start)

        dataSubject.hide()
            .combineWithFavorites(articlesRepository.observeFavoritesIds())
            .bindSubscribeBy(
                onNext = articles::set,
                onError = ::onError
            )
    }

    override fun onNewData(data: List<ArticleListItem>) {
        dataSubject.onNext(data)
        state.set(DATA)
    }

    override fun onError(error: Throwable) {
        state.set(UNKNOWN_ERROR)
    }

    override fun onEmpty() {
        state.set(EMPTY)
    }

    override fun onLoading() {
        state.set(LOADING)
        footer.set(null)
    }

    override fun onNewPage(data: List<ArticleListItem>) {
        val lastValue = dataSubject.value.orEmpty().toMutableList()
        lastValue += data
        dataSubject.onNext(lastValue)
        footer.set(null)
    }

    override fun onPageError(error: Throwable) {
        footer.set(Footer.Error(::onRetryPageClick))
    }

    override fun onPageLoading() {
        footer.set(Footer.Loader)
    }

    override fun onComplete() {
        footer.set(null)
    }

    fun onSwipeRefresh() {
        pagination.start()
    }

    fun onRetryClick() {
        pagination.retry()
    }

    fun onNewPageRequest() {
        pagination.loadNewPage()
    }

    private fun onRetryPageClick() {
        pagination.retryPage()
    }

    private fun requestPage(page: Int): Single<List<ArticleListItem>> {
        return articlesRepository.get(page,
            PAGE_SIZE
        )
            .mapList { it.toListItem(res) }
            .doOnError(Timber::e)
            .observeOn(main)
    }

    override fun onCleared() {
        super.onCleared()
        pagination.clear()
    }

    enum class DataState {
        LOADING,
        DATA,
        UNKNOWN_ERROR,
        CONNECTION_ERROR,
        EMPTY
    }

    companion object {
        const val PAGE_SIZE = 30
    }
}
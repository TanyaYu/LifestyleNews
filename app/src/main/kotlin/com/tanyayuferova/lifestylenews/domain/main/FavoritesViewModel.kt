package com.tanyayuferova.lifestylenews.domain.main

import android.content.res.Resources
import androidx.databinding.ObservableField
import com.tanyayuferova.lifestylenews.data.articles.ArticlesRepository
import com.tanyayuferova.lifestylenews.domain.baseviewmodel.RxViewModel
import com.tanyayuferova.lifestylenews.ui.list.ArticleListItem
import com.tanyayuferova.lifestylenews.ui.list.ArticlesAdapter
import com.tanyayuferova.lifestylenews.utils.mapList
import javax.inject.Inject

/**
 * Author: Tanya Yuferova
 * Date: 7/29/19
 */
class FavoritesViewModel @Inject constructor(
    private val res: Resources,
    private val articlesRepository: ArticlesRepository,
    private val articlesInteraction: ArticleListInteractionViewModel
) : RxViewModel() {

    val articles = ObservableField<List<ArticleListItem>>()
    val state = ObservableField(DataState.DATA)

    //todo move in fragment
    val adapter = ArticlesAdapter(articlesInteraction)

    init {
        loadData()
    }

    private fun loadData() {
        articlesRepository.observeFavorites()
            .mapList { it.toListItem(res) }
            .bindSubscribeBy(
                onNext = { list ->
                    if (list.isEmpty())
                        state.set(DataState.EMPTY)
                    else state.set(DataState.DATA)
                    articles.set(list)
                },
                onError = {
                    state.set(DataState.ERROR)
                }
            )
    }

    fun onRetryClick() {
        loadData()
    }

    enum class DataState {
        DATA,
        ERROR,
        EMPTY
    }
}
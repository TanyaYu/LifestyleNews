package com.tanyayuferova.lifestylenews.domain.main

import android.content.res.Resources
import androidx.databinding.ObservableField
import com.tanyayuferova.lifestylenews.data.articles.ArticlesRepository
import com.tanyayuferova.lifestylenews.domain.baseviewmodel.RxViewModel
import com.tanyayuferova.lifestylenews.ui.list.ArticleListItem
import com.tanyayuferova.lifestylenews.utils.mapList
import com.tanyayuferova.lifestylenews.domain.main.FavoritesViewModel.DataState.*
import javax.inject.Inject

/**
 * Author: Tanya Yuferova
 * Date: 7/29/19
 */
class FavoritesViewModel @Inject constructor(
    private val res: Resources,
    private val articlesRepository: ArticlesRepository
) : RxViewModel() {

    val articles = ObservableField<List<ArticleListItem>>()
    val state = ObservableField(DATA)

    init {
        loadData()
    }

    private fun loadData() {
        articlesRepository.observeFavorites()
            .mapList { it.toListItem(res) }
            .bindSubscribeBy(
                onNext = { list ->
                    if (list.isEmpty())
                        state.set(EMPTY)
                    else state.set(DATA)
                    articles.set(list)
                },
                onError = {
                    state.set(ERROR)
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
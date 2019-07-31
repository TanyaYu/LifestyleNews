package com.tanyayuferova.lifestylenews.domain.favorites

import android.content.res.Resources
import androidx.databinding.ObservableField
import com.tanyayuferova.lifestylenews.data.articles.ArticlesRepository
import com.tanyayuferova.lifestylenews.domain.baseviewmodel.RxViewModel
import com.tanyayuferova.lifestylenews.ui.list.ArticleListItem
import com.tanyayuferova.lifestylenews.ui.list.ArticlesAdapter
import com.tanyayuferova.lifestylenews.ui.main.MainFragmentDirections.Companion.actionMainToDetails
import com.tanyayuferova.lifestylenews.utils.mapList
import javax.inject.Inject

/**
 * Author: Tanya Yuferova
 * Date: 7/29/19
 */
class FavoritesViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    private val res: Resources
) : RxViewModel(),
    ArticlesAdapter.ActionsHandler {

    val articles = ObservableField<List<ArticleListItem>>()
    val state = ObservableField(DataState.DATA)

    //todo move in fragment
    val adapter = ArticlesAdapter(
        actionsHandler = this
    )

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

    override fun onArticleClick(id: Int) {
        navController?.navigate(actionMainToDetails(id))
    }

    override fun onFavoriteClick(id: Int, isFavorite: Boolean) {
        if (isFavorite) {
            articlesRepository.setUnFavorite(id).bindSubscribeBy()
        } else {
            articlesRepository.setFavorite(id).bindSubscribeBy()
        }
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
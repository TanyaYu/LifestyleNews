package com.tanyayuferova.lifestylenews.domain.main

import androidx.navigation.NavController
import com.tanyayuferova.lifestylenews.R
import com.tanyayuferova.lifestylenews.data.articles.ArticlesRepository
import com.tanyayuferova.lifestylenews.domain.common.SnackBarController
import com.tanyayuferova.lifestylenews.domain.baseviewmodel.RxViewModel
import com.tanyayuferova.lifestylenews.ui.list.ArticlesAdapter
import com.tanyayuferova.lifestylenews.ui.main.MainFragmentDirections.Companion.actionMainToBrowse
import com.tanyayuferova.lifestylenews.ui.main.MainFragmentDirections.Companion.actionMainToDetails
import javax.inject.Inject

/**
 * Author: Tanya Yuferova
 * Date: 8/1/19
 */
class ArticleItemsViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    private val navController: NavController,
    private val snackBarController: SnackBarController
) : RxViewModel(), ArticlesAdapter.ActionsHandler {

    override fun onArticleClick(id: Int) {
        navController.navigate(actionMainToDetails(id))
    }

    override fun onFavoriteClick(id: Int, isFavorite: Boolean) {
        if (isFavorite) {
            articlesRepository.setUnFavorite(id).bindSubscribeBy {
                onUnFavoriteComplete(id)
            }
        } else {
            articlesRepository.setFavorite(id).bindSubscribeBy()
        }
    }

    override fun onBrowseClick(id: Int) {
        articlesRepository.getById(id)
            .map { it.url ?: throw Exception("Url not found") }
            .bindSubscribeBy({ url ->
                navController.navigate(actionMainToBrowse(url))
            })
    }

    private fun onUnFavoriteComplete(id: Int) {
        snackBarController.long(R.string.article_removed_message) {
            setAction(R.string.undo_action) {
                articlesRepository.undoUnFavorite(id).bindSubscribeBy()
            }
        }
    }
}
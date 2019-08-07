package com.tanyayuferova.lifestylenews.domain.main

import androidx.navigation.NavController
import com.tanyayuferova.lifestylenews.data.articles.ArticlesRepository
import com.tanyayuferova.lifestylenews.domain.baseviewmodel.RxViewModel
import com.tanyayuferova.lifestylenews.ui.list.ArticlesAdapter
import com.tanyayuferova.lifestylenews.ui.main.MainFragmentDirections.Companion.actionMainToDetails
import javax.inject.Inject

/**
 * Author: Tanya Yuferova
 * Date: 8/1/19
 */
class ArticleItemsViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    private val navController: NavController
) : RxViewModel(), ArticlesAdapter.ActionsHandler {

    override fun onArticleClick(id: Int) {
        navController.navigate(actionMainToDetails(id))
    }

    override fun onFavoriteClick(id: Int, isFavorite: Boolean) {
        if(isFavorite) {
            articlesRepository.setUnFavorite(id).bindSubscribeBy()
            // todo snack bar undo only for favorites
        } else {
            articlesRepository.setFavorite(id).bindSubscribeBy()
        }
    }

    override fun onReadClick(id: Int) {
        navController.navigate(actionMainToDetails(id))
    }
}
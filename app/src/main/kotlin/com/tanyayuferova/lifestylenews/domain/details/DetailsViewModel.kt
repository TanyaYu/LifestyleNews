package com.tanyayuferova.lifestylenews.domain.details

import android.content.res.Resources
import androidx.databinding.ObservableField
import com.tanyayuferova.lifestylenews.data.articles.ArticlesRepository
import com.tanyayuferova.lifestylenews.domain.baseviewmodel.RxViewModel
import com.tanyayuferova.lifestylenews.ui.details.ArticleDetails
import javax.inject.Inject

/**
 * Author: Tanya Yuferova
 * Date: 7/28/2019
 */
class DetailsViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    private val res: Resources
) : RxViewModel() {

    var id: Int = -1

    val article = ObservableField<ArticleDetails>()

    override fun onReady() {
        articlesRepository.getById(id)
            .map { it.toDetails(res) }
            .bindSubscribeBy(article::set)
    }

    fun onBackClick() {
        navController?.navigateUp()
    }
}
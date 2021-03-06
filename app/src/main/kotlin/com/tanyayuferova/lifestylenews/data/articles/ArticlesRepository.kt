package com.tanyayuferova.lifestylenews.data.articles

import com.tanyayuferova.lifestylenews.data.network.exeption.UpgradeRequiredException
import com.tanyayuferova.lifestylenews.domain.entity.Article
import com.tanyayuferova.lifestylenews.utils.mapList
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

/**
 * Author: Tanya Yuferova
 * Date: 7/26/2019
 */
class ArticlesRepository @Inject constructor(
    private val articlesService: ArticlesService,
    private val articlesDao: ArticlesDao
) {

    // TODO query
    fun get(page: Int, limit: Int): Single<List<Article>> {
        return articlesService.everything(
            limit = limit,
            page = page,
            query = "android"
        )
            .map { it.articles }
            .onErrorResumeNext { error ->
                when (error) {
                    is UpgradeRequiredException -> Single.just(emptyList())
                    else -> Single.error(error)
                }
            }
            .mapList { it.toArticle() }
            .toObservable()
            .combineWithFavorites(observeFavoritesIds())
            .firstOrError()
            .doOnSuccess(articlesDao::insertAll)
    }

    fun getById(id: Int): Single<Article> {
        return articlesDao.getById(id)
    }

    fun clearCache() = articlesDao.clear()

    fun observeFavorites() = articlesDao.getFavorites()

    fun observeFavoritesIds() = articlesDao.getFavorites().mapList { it.id }

    fun setFavorite(id: Int) = articlesDao.updateIsFavorite(id, 1, Date())

    fun setUnFavorite(id: Int) = articlesDao.updateIsFavorite(id, 0)

    fun undoUnFavorite(id: Int) = articlesDao.updateIsFavorite(id, 1)
}
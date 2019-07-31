package com.tanyayuferova.lifestylenews.data.articles

import com.tanyayuferova.lifestylenews.domain.entity.Article
import com.tanyayuferova.lifestylenews.ui.list.ArticleListItem
import com.tanyayuferova.lifestylenews.utils.combineLatest
import io.reactivex.Observable

/**
 * Author: Tanya Yuferova
 * Date: 7/31/19
 */

fun Observable<List<Article>>.combineWithFavorites(source: Observable<List<Int>>) : Observable<List<Article>> {
    return this.combineLatest(source) { articles, favorites ->
        articles.map { article ->
            val isFavorite = favorites.contains(article.id)
            if (article.isFavorite == isFavorite) {
                article
            } else {
                article.copy(isFavorite = isFavorite)
            }
        }
    }
}

@JvmName("combineWithFavoritesListItem")
fun Observable<List<ArticleListItem>>.combineWithFavorites(source: Observable<List<Int>>) : Observable<List<ArticleListItem>> {
    return this.combineLatest(source) { articles, favorites ->
        articles.map { article ->
            val isFavorite = favorites.contains(article.id)
            if (article.isFavorite == isFavorite) {
                article
            } else {
                article.copy(isFavorite = isFavorite)
            }
        }
    }
}
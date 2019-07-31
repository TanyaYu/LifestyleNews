package com.tanyayuferova.lifestylenews.ui.list

/**
 * Author: Tanya Yuferova
 * Date: 7/26/2019
 */
data class ArticleListItem(
    val id: Int,
    val title: String,
    val photoUrl: String?,
    val publisher: String,
    val description: String,
    val isFavorite: Boolean
)
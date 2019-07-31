package com.tanyayuferova.lifestylenews.ui.details

/**
 * Author: Tanya Yuferova
 * Date: 7/28/2019
 */
data class ArticleDetails(
    val id: Int,
    val title: String,
    val description: String,
    val published: String,
    val source: String,
    val photoUrl: String,
    val isFavorite: Boolean
)

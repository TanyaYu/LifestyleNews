package com.tanyayuferova.lifestylenews.data.entity

import com.google.gson.annotations.SerializedName

data class ArticlesData(
    @SerializedName("articles")
    val articles: List<ArticleData>,
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Int?,
    @SerializedName("code")
    val errorCode: String?
)
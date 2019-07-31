package com.tanyayuferova.lifestylenews.data.entity

import com.google.gson.annotations.SerializedName
import com.tanyayuferova.lifestylenews.domain.entity.Article
import com.tanyayuferova.lifestylenews.utils.DateFormatter

data class ArticleData(
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("source")
    val source: SourceData?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?
) {
    fun toArticle() = Article(
        id = (title + description).hashCode(),
        sourceName = source?.name,
        author = author,
        title = title.orEmpty(),
        description = description,
        url = url,
        photoUrl = urlToImageFixed,
        published = publishedAt?.let { DateFormatter.stringToISO8601Format(publishedAt) },
        isFavorite = false,
        addedToFavored = null
    )

    private val urlToImageFixed: String?
        get() {
            return if (
                !urlToImage.isNullOrEmpty()
                && !urlToImage.startsWith("https:")
                && !urlToImage.startsWith("http:")
            ) {
                "https:$urlToImage"
            } else {
                urlToImage
            }
        }
}
package com.tanyayuferova.lifestylenews.domain.entity

import android.content.res.Resources
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tanyayuferova.lifestylenews.R
import com.tanyayuferova.lifestylenews.ui.list.ArticleListItem
import com.tanyayuferova.lifestylenews.ui.details.ArticleDetails
import com.tanyayuferova.lifestylenews.utils.DateFormatter
import java.util.Date
import java.util.concurrent.TimeUnit
import kotlin.math.round

/**
 * Author: Tanya Yuferova
 * Date: 7/27/2019
 */
@Entity(tableName = "article")
data class Article(
    @PrimaryKey
    val id: Int,
    val sourceName: String?,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String?,
    val photoUrl: String?,
    val published: Date?,
    val isFavorite: Boolean = false,
    val addedToFavored: Date?
) {

    fun toListItem(res: Resources) = ArticleListItem(
        id = id,
        title = title,
        photoUrl = photoUrl,
        publisher = getListItemPublisher(res),
        description = description.orEmpty(),
        isFavorite = isFavorite,
        hasUrl = !url.isNullOrEmpty()
    )

    fun toDetails(res: Resources) = ArticleDetails(
        id = id,
        title = title,
        description = description.orEmpty(),
        source = getSourceFormatted(res).orEmpty(),
        published = getDetailsPublisher(res),
        photoUrl = photoUrl.orEmpty(),
        isFavorite = isFavorite
    )

    private fun getSourceFormatted(res: Resources) = sourceName?.let {
        res.getString(R.string.source_link, sourceName, url)
    }

    private fun getPublishedFormatted(res: Resources) = published?.let {
        res.getString(
            R.string.on_date,
            DateFormatter.formatShort(published)
        )
    }

    private fun getPublishedPeriod(res: Resources):String? = published?.let {
        val now = Date()
        val timeSpend = now.time - published.time

        val hours = TimeUnit.MILLISECONDS.toHours(timeSpend).toInt()
        if (hours < 24) {
            return res.getQuantityString(R.plurals.hours_ago, hours, hours)
        }
        val days = TimeUnit.MILLISECONDS.toDays(timeSpend).toInt()
        if (days < 7) {
            return res.getQuantityString(R.plurals.days_ago, days, days)
        }
        val weeks = round(days / 7f).toInt()
        if (weeks < 6) {
            return res.getQuantityString(R.plurals.weeks_ago, weeks, weeks)
        }
        // approximate
        val months = round(days / 30f).toInt()
        if (months < 12) {
            return res.getQuantityString(R.plurals.months_ago, months.toInt(), months)
        }
        val years = round(months / 12f).toInt()
        return res.getQuantityString(R.plurals.years_ago, years.toInt(), years)
    }

    private fun getAuthorFormatted(res: Resources) = author?.let {
        res.getString(R.string.by_author, author)
    }

    private fun getListItemPublisher(res: Resources) = arrayListOf(
        getSourceFormatted(res),
        getPublishedPeriod(res)
    )
        .filterNot { it.isNullOrBlank() }
        .joinToString(" • ")

    private fun getDetailsPublisher(res: Resources) = arrayListOf(
        getAuthorFormatted(res),
        getPublishedFormatted(res)
    )
        .filterNot { it.isNullOrBlank() }
        .joinToString(" ")
}

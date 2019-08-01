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
        publisher = getPublisherFormatted(res),
        description = description.orEmpty(),
        isFavorite = isFavorite
    )

    fun toDetails(res: Resources) = ArticleDetails(
        id = id,
        title = title,
        description = description.orEmpty(),
        source = getSourceFormatted(res),
        published = getAuthorFormatted(res) + " " + getPublishedFormatted(res),
        photoUrl = photoUrl.orEmpty(),
        isFavorite = isFavorite
    )

    private fun getSourceFormatted(res: Resources) = sourceName?.let {
        res.getString(R.string.source_link, sourceName, url)
    }.orEmpty()

    private fun getPublishedFormatted(res: Resources) = published?.let {
        res.getString(
            R.string.on_date,
            DateFormatter.formatShort(published)
        )
    }.orEmpty()

    //todo move logic
    private fun getPublishedPeriod(res: Resources) = published?.let {
        val now = Date()
        val timeSpend = now.time - published.time

        val hours = TimeUnit.MILLISECONDS.toHours(timeSpend)
        if (hours < 24) {
            return res.getQuantityString(R.plurals.hours_ago, hours.toInt(), hours)
        }
        val days = TimeUnit.MILLISECONDS.toDays(timeSpend)
        if (days < 7) {
            return res.getQuantityString(R.plurals.days_ago, days.toInt(), days)
        }
        val weeks = days / 7
        if (weeks < 6) {
            return res.getQuantityString(R.plurals.weeks_ago, weeks.toInt(), weeks)
        }
        //todo round
        val months = days / 30 //todo think of a better way
        if (months < 12) {
            return res.getQuantityString(R.plurals.months_ago, months.toInt(), months)
        }
        val years = months / 12
        return res.getQuantityString(R.plurals.years_ago, years.toInt(), years)
    }.orEmpty()

    private fun getAuthorFormatted(res: Resources) = author?.let {
        res.getString(R.string.by_author, author)
    }.orEmpty()

    private fun getPublisherFormatted(res: Resources) = arrayListOf(
        getSourceFormatted(res),
        getPublishedPeriod(res)
    )
        .filter { it.isNotBlank() }
        .joinToString(" • ")
}

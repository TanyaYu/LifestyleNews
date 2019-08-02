package com.tanyayuferova.lifestylenews.data.articles

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tanyayuferova.lifestylenews.domain.entity.Article
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

/**
 * Author: Tanya Yuferova
 * Date: 7/28/2019
 */
@Dao
interface ArticlesDao {

    @Query("SELECT * FROM article")
    fun getAll(): Flowable<List<Article>>

    @Query("SELECT * FROM article WHERE id = :id")
    fun getById(id: Int): Single<Article>

    @Query("update article SET isFavorite = :isFavorite WHERE id = :id")
    fun updateIsFavorite(id: Int, isFavorite: Int): Completable

    @Query("update article SET isFavorite = :isFavorite, addedToFavored = :dateAdded WHERE id = :id")
    fun updateIsFavorite(id: Int, isFavorite: Int, dateAdded: Date?): Completable

    @Query("SELECT * FROM article WHERE isFavorite = 1 ORDER BY addedToFavored DESC")
    fun getFavorites(): Observable<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(virtues: List<Article>)

    @Query("DELETE FROM article WHERE isFavorite = 0")
    fun clear(): Completable
}
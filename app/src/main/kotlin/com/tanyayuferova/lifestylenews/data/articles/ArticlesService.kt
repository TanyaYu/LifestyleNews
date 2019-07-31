package com.tanyayuferova.lifestylenews.data.articles

import com.tanyayuferova.lifestylenews.data.entity.ArticlesData
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Author: Tanya Yuferova
 * Date: 7/28/2019
 */
interface ArticlesService {

    @GET("everything")
    fun everything(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("q") query: String
    ): Single<ArticlesData>
}
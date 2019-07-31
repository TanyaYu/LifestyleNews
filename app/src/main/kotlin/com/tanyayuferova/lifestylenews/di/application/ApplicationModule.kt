package com.tanyayuferova.lifestylenews.di.application

import android.content.Context
import android.content.res.Resources
import com.tanyayuferova.lifestylenews.Application
import com.tanyayuferova.lifestylenews.data.BASE_URL
import com.tanyayuferova.lifestylenews.data.articles.ArticlesService
import com.tanyayuferova.lifestylenews.data.network.ApiKeyInterceptor
import com.tanyayuferova.lifestylenews.di.activity.ActivityScope
import com.tanyayuferova.lifestylenews.di.activity.FragmentBuildersModule
import com.tanyayuferova.lifestylenews.domain.common.Schedulers
import com.tanyayuferova.lifestylenews.ui.activity.MainActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Author: Tanya Yuferova
 * Date: 7/27/2019
 */
@Module
abstract class ApplicationModule {
    @Binds
    internal abstract fun provideContext(application: Application): Context

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    @ActivityScope
    internal abstract fun bindMainActivity(): MainActivity

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideResources(context: Context): Resources {
            return context.resources
        }

        @Provides
        @JvmStatic
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(ApiKeyInterceptor())
                .build()
        }

        @Provides
        @JvmStatic
        fun provideArticlesService(httpClient: OkHttpClient): ArticlesService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io))
                .client(httpClient)
                .build()
                .create(ArticlesService::class.java)
        }
    }

}

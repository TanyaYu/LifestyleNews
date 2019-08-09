package com.tanyayuferova.lifestylenews.di.application

import android.content.Context
import android.content.res.Resources
import com.tanyayuferova.lifestylenews.Application
import com.tanyayuferova.lifestylenews.data.BASE_URL
import com.tanyayuferova.lifestylenews.data.articles.ArticlesService
import com.tanyayuferova.lifestylenews.data.network.ApiKeyInterceptor
import com.tanyayuferova.lifestylenews.data.network.ErrorInterceptor
import com.tanyayuferova.lifestylenews.data.network.status.NetworkStatusReceiver
import com.tanyayuferova.lifestylenews.data.network.status.NetworkStatusService
import com.tanyayuferova.lifestylenews.di.activity.ActivityScope
import com.tanyayuferova.lifestylenews.di.activity.FragmentBuildersModule
import com.tanyayuferova.lifestylenews.domain.activityCommand.ActivityCommandsExecutor
import com.tanyayuferova.lifestylenews.domain.activityCommand.ActivityCommandsExecutorImpl
import com.tanyayuferova.lifestylenews.domain.activityCommand.ActivityHolder
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
import javax.inject.Singleton


/**
 * Author: Tanya Yuferova
 * Date: 7/27/2019
 */
@Module
abstract class ApplicationModule {

    @Binds
    internal abstract fun provideContext(application: Application): Context

    @ContributesAndroidInjector(modules = [MainActivityModule::class, FragmentBuildersModule::class])
    @ActivityScope
    internal abstract fun bindMainActivity(): MainActivity

    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun provideResources(context: Context): Resources {
            return context.resources
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideOkHttpClient(networkStatusService: NetworkStatusService): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(ApiKeyInterceptor())
                .addInterceptor(ErrorInterceptor(networkStatusService))
                .build()
        }

        @Provides
        @Singleton
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

        @Provides
        @Singleton
        @JvmStatic
        fun provideNetworkStatusReceiver(context: Context): NetworkStatusReceiver {
            return NetworkStatusReceiver(context)
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideNetworkStatusService(networkStatusReceiver: NetworkStatusReceiver): NetworkStatusService {
            return networkStatusReceiver
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideActivityCommandsExecutor() : ActivityCommandsExecutor {
            return ActivityCommandsExecutorImpl()
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideActivityHolder(executor: ActivityCommandsExecutor) : ActivityHolder {
            return executor
        }
    }
}

package com.tanyayuferova.lifestylenews.di.application

import com.tanyayuferova.lifestylenews.Application
import com.tanyayuferova.lifestylenews.di.database.DatabaseModule
import com.tanyayuferova.lifestylenews.di.viewmodel.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Author: Tanya Yuferova
 * Date: 7/27/2019
 */
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    DatabaseModule::class,
    ViewModelModule::class
])
@Singleton
interface ApplicationComponent : AndroidInjector<Application> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<Application>()
}
package com.tanyayuferova.lifestylenews.di.application

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.tanyayuferova.lifestylenews.R
import com.tanyayuferova.lifestylenews.ui.activity.MainActivity
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Author: Tanya Yuferova
 * Date: 8/1/19
 */
@Module
abstract class MainActivityModule {
    @Binds
    internal abstract fun provideMainActivity(activity: MainActivity): AppCompatActivity

    @Module companion object {
        @Provides
        @JvmStatic
        fun provideNavController(activity: AppCompatActivity) : NavController {
            return activity.findNavController(R.id.nav_host_fragment)
        }
    }
}
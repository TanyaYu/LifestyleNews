package com.tanyayuferova.lifestylenews.di.activity

import com.tanyayuferova.lifestylenews.ui.favorites.FavoritesFragment
import com.tanyayuferova.lifestylenews.ui.list.ListFragment
import com.tanyayuferova.lifestylenews.ui.main.MainFragment
import com.tanyayuferova.lifestylenews.ui.details.DetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Author: Tanya Yuferova
 * Date: 7/28/2019
 */
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    internal abstract fun bindMainFragment(): MainFragment

    @ContributesAndroidInjector
    internal abstract fun bindListFragment(): ListFragment

    @ContributesAndroidInjector
    internal abstract fun bindFavoritesFragment(): FavoritesFragment

    @ContributesAndroidInjector
    internal abstract fun bindDetailsFragment(): DetailsFragment
}
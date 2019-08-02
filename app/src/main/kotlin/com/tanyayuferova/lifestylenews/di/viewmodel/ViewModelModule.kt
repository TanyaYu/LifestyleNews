package com.tanyayuferova.lifestylenews.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tanyayuferova.lifestylenews.domain.main.ArticleItemsViewModel
import com.tanyayuferova.lifestylenews.domain.main.FavoritesViewModel
import com.tanyayuferova.lifestylenews.domain.main.ListViewModel
import com.tanyayuferova.lifestylenews.domain.main.MainViewModel
import com.tanyayuferova.lifestylenews.domain.baseviewmodel.ViewModelProviderFactory
import com.tanyayuferova.lifestylenews.domain.details.DetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Author: Tanya Yuferova
 * Date: 7/27/2019
 */
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    internal abstract fun bindListViewModel(viewModel: ListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    internal abstract fun bindDetailViewModel(viewModel: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    internal abstract fun bindFavoritesViewModel(viewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArticleItemsViewModel::class)
    internal abstract fun bindArticleListInteractionViewModel(viewModel: ArticleItemsViewModel): ViewModel
}
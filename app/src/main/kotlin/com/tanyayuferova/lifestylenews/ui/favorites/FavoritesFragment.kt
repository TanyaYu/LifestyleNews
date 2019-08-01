package com.tanyayuferova.lifestylenews.ui.favorites

import com.tanyayuferova.lifestylenews.R
import com.tanyayuferova.lifestylenews.databinding.FragmentFavoritesBinding
import com.tanyayuferova.lifestylenews.domain.main.ArticleItemsViewModel
import com.tanyayuferova.lifestylenews.domain.main.FavoritesViewModel
import com.tanyayuferova.lifestylenews.ui.base.ViewModelFragment
import com.tanyayuferova.lifestylenews.ui.common.provideViewModel
import com.tanyayuferova.lifestylenews.ui.list.ArticlesAdapter

/**
 * Author: Tanya Yuferova
 * Date: 7/29/19
 */
class FavoritesFragment : ViewModelFragment<FragmentFavoritesBinding, FavoritesViewModel>() {
    override val layout = R.layout.fragment_favorites
    override val viewModelClass = FavoritesViewModel::class.java

    private lateinit var articleItemsViewModel: ArticleItemsViewModel

    override fun onBindingCreated(binding: FragmentFavoritesBinding) {
        articleItemsViewModel = provideViewModel(viewModelFactory)
        binding.list.adapter = ArticlesAdapter(articleItemsViewModel)
    }
}
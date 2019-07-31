package com.tanyayuferova.lifestylenews.ui.favorites

import com.tanyayuferova.lifestylenews.R
import com.tanyayuferova.lifestylenews.databinding.FragmentFavoritesBinding
import com.tanyayuferova.lifestylenews.domain.favorites.FavoritesViewModel
import com.tanyayuferova.lifestylenews.ui.base.ViewModelFragment

/**
 * Author: Tanya Yuferova
 * Date: 7/29/19
 */
class FavoritesFragment : ViewModelFragment<FragmentFavoritesBinding, FavoritesViewModel>() {
    override val layout = R.layout.fragment_favorites
    override val viewModelClass = FavoritesViewModel::class.java
}
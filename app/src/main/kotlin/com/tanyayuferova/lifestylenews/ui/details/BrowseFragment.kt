package com.tanyayuferova.lifestylenews.ui.details

import androidx.navigation.fragment.navArgs
import com.tanyayuferova.lifestylenews.R
import com.tanyayuferova.lifestylenews.databinding.FragmentBrowseBinding
import com.tanyayuferova.lifestylenews.domain.details.BrowseViewModel
import com.tanyayuferova.lifestylenews.ui.base.ViewModelFragment

/**
 * Author: Tanya Yuferova
 * Date: 8/7/19
 */
class BrowseFragment : ViewModelFragment<FragmentBrowseBinding, BrowseViewModel>() {

    override val layout = R.layout.fragment_browse
    override val viewModelClass = BrowseViewModel::class.java
    private val args: BrowseFragmentArgs by navArgs()

    override fun bindArgs(viewModel: BrowseViewModel) {
        viewModel.url = args.url
    }
}
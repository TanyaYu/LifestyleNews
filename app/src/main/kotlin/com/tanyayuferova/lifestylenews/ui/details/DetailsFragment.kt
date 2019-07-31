package com.tanyayuferova.lifestylenews.ui.details

import androidx.navigation.fragment.navArgs
import com.tanyayuferova.lifestylenews.R
import com.tanyayuferova.lifestylenews.databinding.FragmentDetailsBinding
import com.tanyayuferova.lifestylenews.domain.details.DetailsViewModel
import com.tanyayuferova.lifestylenews.ui.base.ViewModelFragment

/**
 * Author: Tanya Yuferova
 * Date: 7/28/2019
 */
class DetailsFragment : ViewModelFragment<FragmentDetailsBinding, DetailsViewModel>() {

    override val layout = R.layout.fragment_details
    override val viewModelClass = DetailsViewModel::class.java
    private val args: DetailsFragmentArgs by navArgs()

    override fun bindArgs(viewModel: DetailsViewModel) {
        viewModel.id = args.id
    }
}
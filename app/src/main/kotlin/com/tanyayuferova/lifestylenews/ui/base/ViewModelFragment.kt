package com.tanyayuferova.lifestylenews.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tanyayuferova.lifestylenews.BR
import com.tanyayuferova.lifestylenews.domain.baseviewmodel.NavigationViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Author: Tanya Yuferova
 * Date: 7/28/2019
 */
abstract class ViewModelFragment<Binding : ViewDataBinding, ViewModel : NavigationViewModel>
    : DaggerFragment() {

    abstract val layout: Int
    abstract val viewModelClass: Class<ViewModel>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ViewModel
    private lateinit var binding: Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[viewModelClass]
        viewModel.navController = findNavController()
        bindArgs(viewModel)
        viewModel.onReady()

        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        onBindingCreated(binding)
        bindViewModel(binding, viewModel)
        return binding.root
    }

    // fixme temp solution
    open fun bindArgs(viewModel: ViewModel) {

    }

    open fun onBindingCreated(binding: Binding) {

    }

    open fun bindViewModel(binding: Binding, viewModel: ViewModel) {
        binding.setVariable(BR.vm, viewModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.navController = null
    }
}
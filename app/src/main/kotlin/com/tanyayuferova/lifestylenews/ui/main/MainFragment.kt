package com.tanyayuferova.lifestylenews.ui.main

import com.tanyayuferova.lifestylenews.R
import com.tanyayuferova.lifestylenews.databinding.FragmentMainBinding
import com.tanyayuferova.lifestylenews.domain.main.MainViewModel
import com.tanyayuferova.lifestylenews.ui.base.ViewModelFragment

/**
 * Author: Tanya Yuferova
 * Date: 7/29/19
 */
class MainFragment : ViewModelFragment<FragmentMainBinding, MainViewModel>() {

    override val layout = R.layout.fragment_main
    override val viewModelClass = MainViewModel::class.java

    override fun onBindingCreated(binding: FragmentMainBinding)  = with(binding) {
        viewPager.adapter = MainTabsAdapter(childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }
}
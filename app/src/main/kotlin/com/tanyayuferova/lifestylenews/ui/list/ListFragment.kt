package com.tanyayuferova.lifestylenews.ui.list

import com.tanyayuferova.lifestylenews.R
import com.tanyayuferova.lifestylenews.databinding.FragmentListBinding
import com.tanyayuferova.lifestylenews.domain.list.ListViewModel
import com.tanyayuferova.lifestylenews.ui.base.ViewModelFragment

class ListFragment : ViewModelFragment<FragmentListBinding, ListViewModel>() {

    override val layout = R.layout.fragment_list
    override val viewModelClass = ListViewModel::class.java
}

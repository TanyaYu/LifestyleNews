package com.tanyayuferova.lifestylenews.ui.list

import com.tanyayuferova.lifestylenews.R
import com.tanyayuferova.lifestylenews.databinding.FragmentListBinding
import com.tanyayuferova.lifestylenews.domain.main.ArticleItemsViewModel
import com.tanyayuferova.lifestylenews.domain.main.ListViewModel
import com.tanyayuferova.lifestylenews.ui.base.ViewModelFragment
import com.tanyayuferova.lifestylenews.ui.common.PaginationWrapperAdapter
import com.tanyayuferova.lifestylenews.ui.common.provideViewModel

class ListFragment : ViewModelFragment<FragmentListBinding, ListViewModel>() {

    override val layout = R.layout.fragment_list
    override val viewModelClass = ListViewModel::class.java

    private lateinit var articleItemsViewModel: ArticleItemsViewModel

    override fun onBindingCreated(binding: FragmentListBinding) {
        articleItemsViewModel = provideViewModel(viewModelFactory)
    }

    override fun bindViewModel(binding: FragmentListBinding, viewModel: ListViewModel) {
        super.bindViewModel(binding, viewModel)
        binding.list.adapter = PaginationWrapperAdapter(
            pageSize = ListViewModel.PAGE_SIZE,
            newPageRequest = viewModel::onNewPageRequest,
            adapter = ArticlesAdapter(articleItemsViewModel)
        )
    }
}

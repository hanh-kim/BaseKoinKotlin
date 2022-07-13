package com.hanhpk.basekoinkotlin.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.hanhpk.basekoinkotlin.adapter.userPost.UserPostAdapter
import com.hanhpk.basekoinkotlin.adapter.userPost.UserPostPagingAdapter
import com.hanhpk.basekoinkotlin.base.BaseFragment
import com.hanhpk.basekoinkotlin.databinding.FragmentMainBinding
import com.hanhpk.basekoinkotlin.extensions.observe
import com.hanhpk.basekoinkotlin.pagingsources.UserPostPagingSource
import com.hanhpk.basekoinkotlin.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainFragment : BaseFragment<FragmentMainBinding>() {

//    private val viewModel:MainViewModel by viewModel()
    private lateinit var viewModel: MainViewModel

    private val pagingAdapter : UserPostPagingAdapter by lazy { UserPostPagingAdapter() }
    private val adapter : UserPostAdapter by lazy { UserPostAdapter() }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun initViews(viewBinding: FragmentMainBinding) {
        //init viewModel with constructor has a attr Id :Int
        viewModel = getViewModel { parametersOf(0) }
    }

    override fun initData() {
        with(viewModel) {
            getUserPost(1)

            observe(userPostLiveData) {
                it?.let {
                    //TODO somethings: show list data
                    adapter.setItems(it)
                }
            }

            observe(userPostPaging){
                it?.let {
                    lifecycleScope.launchWhenStarted {
                        pagingAdapter.submitData(it)
                    }
                }
            }
        }

        //todo when wanna recall api, refresh list paging
        // pagingAdapter.refresh()
    }

    override fun initListeners() {

        // load state paging listener
        pagingAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                // todo loading data
            } else {
               //on finished load data
            }
        }
    }
}
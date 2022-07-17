package com.hanhpk.basekoinkotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.faltenreich.skeletonlayout.createSkeleton
import com.google.android.material.appbar.AppBarLayout
import com.hanhpk.basekoinkotlin.R
import com.hanhpk.basekoinkotlin.adapter.ExampleLoadStateAdapter
import com.hanhpk.basekoinkotlin.adapter.userPost.PhotoPagingAdapter
import com.hanhpk.basekoinkotlin.base.BaseFragment
import com.hanhpk.basekoinkotlin.common.SmoothAppBarBehavior
import com.hanhpk.basekoinkotlin.databinding.FragmentMainBinding
import com.hanhpk.basekoinkotlin.extensions.observe
import com.hanhpk.basekoinkotlin.ui.customview.HeaderView
import com.hanhpk.basekoinkotlin.ui.customview.SegmentedControlView
import com.hanhpk.basekoinkotlin.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class MainFragment : BaseFragment<FragmentMainBinding>() {

//    private val viewModel:MainViewModel by viewModel()
    private lateinit var viewModel: MainViewModel

    private val pagingAdapter =PhotoPagingAdapter()
    private lateinit var rcvSkeleton: Skeleton
    private lateinit var viewSkeleton: Skeleton

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun initViews(viewBinding: FragmentMainBinding) {
        //init viewModel with constructor has a attr Id :Int
        viewModel = getViewModel { parametersOf(0) }
        binding.rcvData.adapter = pagingAdapter
        val concatAdapter = pagingAdapter.withLoadStateFooter(ExampleLoadStateAdapter())
        binding.rcvData.adapter = concatAdapter

        val behavior = SmoothAppBarBehavior()
        behavior.recyclerView = binding.rcvData
        val layoutParams = binding.appBar.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = behavior

        binding.viewSegmentControl.initTabs(
            object : SegmentedControlView.IMenuTabOnClickListener {
                override fun onClick(tabId: Int) {
                   //todo somethings: call api,..
                }
            },
            resources.getStringArray(R.array.segment_control_main)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSkeleton()
    }

    override fun initData() {
        with(viewModel) {
         //   getPhotos(1)

            observe(photoLiveData) {
                it?.let {
                    //TODO somethings: show list data
                   // adapter.setItems(it)

                }
                //hide skeleton when get data successfully
               // showOriginal()
            }

            observe(failure){
                showOriginal()
            }

            observe(totalCount){
                it?.let {
                    binding.message.text = "TotalCount: $it"
                }
                showOriginal()
            }

            observe(photoPaging){
                lifecycleScope.launchWhenStarted {
                    it?.let {
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
                showProgressBar(binding.progressBar)
            } else {
               //on finished load data
                showOriginal()
            }
        }

        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            binding.refreshLayout.isEnabled = verticalOffset==0
        })

        binding.refreshLayout.setOnRefreshListener {
            binding.refreshLayout.isRefreshing = false
            showProgressBar(binding.progressBar)
            pagingAdapter.refresh()
        }

        binding.header.listener =object: HeaderView.IOnMenuClickListener{
            override fun onLeftMenuClick() {
                //backPress
            }

            override fun onRightMenuClick() {

            }
        }
    }

    private fun showSkeleton() {
        viewSkeleton = binding.viewSkeleton.createSkeleton()
//        rcvSkeleton = binding.rcvData.applySkeleton(R.layout.item_photo)
        viewSkeleton.showSkeleton()
        //rcvSkeleton.showSkeleton()
        showProgressBar(binding.progressBar)
    }

    private fun showOriginal() {
        viewSkeleton.showOriginal()
        //rcvSkeleton.showOriginal()
        hideProgressBar(binding.progressBar)
    }

}
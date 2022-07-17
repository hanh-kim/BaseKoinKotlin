package com.hanhpk.basekoinkotlin.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.hanhpk.basekoinkotlin.R
import com.hanhpk.basekoinkotlin.adapter.BaseFragmentStatePagerAdapter
import com.hanhpk.basekoinkotlin.base.BaseFragment
import com.hanhpk.basekoinkotlin.base.BaseFragmentPagerModel
import com.hanhpk.basekoinkotlin.databinding.FragmentSecondBinding
import com.hanhpk.basekoinkotlin.extensions.reduceDragSensitivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SecondFragment : BaseFragment<FragmentSecondBinding>() {

    private val innerTabFragments: List<BaseFragmentPagerModel> by lazy {
        listOf(
            BaseFragmentPagerModel(
                InnerTabFragment.newInstance("InnerTab1"),
                getString(R.string.tab_inner_1),
            ),
            BaseFragmentPagerModel(
                InnerTabFragment.newInstance("InnerTab2"),
                getString(R.string.tab_inner_2)
            )
        )
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSecondBinding {
        return FragmentSecondBinding.inflate(inflater, container, false)
    }

    override fun initViews(viewBinding: FragmentSecondBinding) {

        binding.pager.reduceDragSensitivity()
        //Init tab layout, pages
        binding.pager.adapter = context?.let {
            BaseFragmentStatePagerAdapter(
                it, innerTabFragments, this
            )
        }
        binding.pager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
//                binding.tabLayout.selectTab()
            }
        })

        TabLayoutMediator(binding.tabLayout, binding.pager, false, false) { tab, position ->
            binding.tabLayout.setupTabLayout(tab, innerTabFragments[position])
        }.attach()
    }

    override fun initData() {
    }

    override fun initListeners() {

    }
}
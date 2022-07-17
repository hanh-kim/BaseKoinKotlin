package com.hanhpk.basekoinkotlin.ui.activity

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hanhpk.basekoinkotlin.R
import com.hanhpk.basekoinkotlin.adapter.BaseFragmentStatePagerAdapter
import com.hanhpk.basekoinkotlin.base.BaseActivity
import com.hanhpk.basekoinkotlin.base.BaseDataBindingActivity
import com.hanhpk.basekoinkotlin.base.BaseFragmentPagerModel
import com.hanhpk.basekoinkotlin.databinding.ActivityMainBinding
import com.hanhpk.basekoinkotlin.ui.fragments.MainFragment
import com.hanhpk.basekoinkotlin.ui.fragments.SecondFragment
import com.hanhpk.basekoinkotlin.ui.fragments.ThirdFragment
import com.hanhpk.basekoinkotlin.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseDataBindingActivity<ActivityMainBinding, MainActivityViewModel>(){

    private val mainFragments = mutableListOf<BaseFragmentPagerModel>()

    override val viewModel: MainActivityViewModel by viewModel()

    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override fun initView() {
        lifecycle.addObserver(viewModel)
        createPageFragments()

        binding.viewPager.adapter = BaseFragmentStatePagerAdapter(this, mainFragments, supportFragmentManager, lifecycle)
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.offscreenPageLimit = 5
        binding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.navigation.menu.getItem(position).isChecked = true
            }
        })
        binding.navigation.itemIconTintList = null
        binding.navigation.setOnItemSelectedListener(this::navigationItemSelected)

        // show dot when receive new notification
        showNotificationBadge()
    }


    private fun navigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                binding.viewPager.setCurrentItem(0, false)
            }
            R.id.navigation_search -> {
                binding.viewPager.setCurrentItem(1, false)
            }
            R.id.navigation_notification -> {
                binding.viewPager.setCurrentItem(2, false)
            }
            else -> return false
        }
        return true
    }

    private fun createPageFragments() {
        mainFragments.add(
            BaseFragmentPagerModel(
                MainFragment()
            )
        )
        mainFragments.add(
            BaseFragmentPagerModel(
                SecondFragment()
            )
        )
        mainFragments.add(
            BaseFragmentPagerModel(
                ThirdFragment()
            )
        )
    }

    fun moveToPage(pageIndex: Int){
        binding.viewPager.setCurrentItem(pageIndex, false)
        selectTab(pageIndex)
    }

    private fun selectTab(tabIndex: Int){
        binding.navigation.menu.getItem(tabIndex).isChecked = true
    }

    private fun showNotificationBadge() {
        showBadge(binding.navigation, R.id.navigation_notification)
    }

    private fun removeNotificationBadge() {
        removeBadge(binding.navigation, R.id.navigation_notification)
    }

    private fun showBadge(bottomNavigationView: BottomNavigationView, @IdRes itemId: Int) {
        removeBadge(bottomNavigationView, itemId)
        val itemView = bottomNavigationView.findViewById<BottomNavigationItemView>(itemId)
        val badge = LayoutInflater.from(this)
            .inflate(R.layout.information_badge, bottomNavigationView, false)
        itemView.addView(badge)
    }

    private fun removeBadge(bottomNavigationView: BottomNavigationView, @IdRes itemId: Int) {
        val itemView = bottomNavigationView.findViewById<BottomNavigationItemView>(itemId)
        if (itemView.childCount == 3) {
            itemView.removeViewAt(2)
        }
    }



}
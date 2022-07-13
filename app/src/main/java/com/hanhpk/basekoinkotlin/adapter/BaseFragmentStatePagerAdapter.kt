package com.hanhpk.basekoinkotlin.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hanhpk.basekoinkotlin.base.BaseFragmentPagerModel

open class BaseFragmentStatePagerAdapter<T> : FragmentStateAdapter {
    private var context: Context
    private var itemsSource: List<T>

    constructor(
        context: Context,
        itemsSource: MutableList<T>,
        fragmentManager: FragmentManager?,
        lifecycle: Lifecycle?
    ) : super(
        fragmentManager!!, lifecycle!!
    ) {
        this.context = context
        this.itemsSource = itemsSource
    }

    constructor(
        context: Context,
        itemsSource: List<T>,
        fragment: Fragment?,
    ) : super(
        fragment!!
    ) {
        this.context = context
        this.itemsSource = itemsSource
    }

    override fun createFragment(position: Int): Fragment {
        return (itemsSource[position] as BaseFragmentPagerModel).fragment
    }

    override fun getItemCount(): Int {
        return itemsSource.size
    }
}
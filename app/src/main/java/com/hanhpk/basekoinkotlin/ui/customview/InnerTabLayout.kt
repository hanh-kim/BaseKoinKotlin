package com.hanhpk.basekoinkotlin.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.hanhpk.basekoinkotlin.R
import com.hanhpk.basekoinkotlin.base.BaseFragmentPagerModel

class InnerTabLayout(context: Context, attrs: AttributeSet? = null): TabLayout(context, attrs), TabLayout.OnTabSelectedListener {

    init {
        addOnTabSelectedListener(this)
    }

    override fun newTab(): Tab {
        val tab = super.newTab()
        tab.setCustomView(R.layout.layout_inner_tab)
        return tab
    }

    override fun onTabSelected(tab: Tab?) {
        tab?.customView?.findViewById<TextView>(R.id.tv_tab_name)?.typeface = context.resources.getFont(R.font.roboto_medium)
    }

    override fun onTabUnselected(tab: Tab?) {
        tab?.customView?.findViewById<TextView>(R.id.tv_tab_name)?.typeface = context.resources.getFont(R.font.roboto_medium)
    }

    override fun onTabReselected(tab: Tab?) {
        // nothing to do
    }

    fun setupTabLayout(tab: Tab, item: BaseFragmentPagerModel) {
        tab.customView?.findViewById<TextView>(R.id.tv_tab_name)?.text = item.title
        tab.customView?.findViewById<ImageView>(R.id.iv_tab_icon)?.visibility = if (item.showIcon) View.VISIBLE else View.GONE
        tab.customView?.findViewById<View>(R.id.badge)?.visibility = if (item.showBadge) View.VISIBLE else View.GONE
        tab.customView?.findViewById<View>(R.id.tv_tab_batch)?.visibility = if (item.showBatch) View.VISIBLE else View.GONE
    }

    fun setupTabLayout(tab: Tab, title: String, showIcon: Boolean = false, showBadge: Boolean = false) {
        tab.customView?.findViewById<TextView>(R.id.tv_tab_name)?.text = title
        tab.customView?.findViewById<ImageView>(R.id.iv_tab_icon)?.visibility = if (showIcon) View.VISIBLE else View.GONE
        tab.customView?.findViewById<View>(R.id.badge)?.visibility = if (showBadge) View.VISIBLE else View.GONE
    }
}
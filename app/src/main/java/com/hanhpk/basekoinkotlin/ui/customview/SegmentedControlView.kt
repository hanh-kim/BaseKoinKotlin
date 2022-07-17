package com.hanhpk.basekoinkotlin.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.hanhpk.basekoinkotlin.R


class SegmentedControlView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var currentTabIndex = 0
    private val tabList = ArrayList<View>()
    private val tabEnable = ArrayList<Boolean>()
    private var listener: IMenuTabOnClickListener? = null
    private var tabLayoutParams: LayoutParams? = null
    private var tabPaddingVertical = 0f

    interface IMenuTabOnClickListener {
        fun onClick(tabId: Int)
    }

    init {
        View.inflate(context, R.layout.segment_control_view, this)
        tabLayoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT)
        tabLayoutParams?.gravity = Gravity.CENTER
        tabLayoutParams?.weight = 1.0f

        if (attrs != null) {
            val attr = context.obtainStyledAttributes(attrs, R.styleable.SegmentedControlView)
            try {
                if (attr.hasValue(R.styleable.SegmentedControlView_segment_tab_padding_vertical)) {
                    tabPaddingVertical = attr.getDimension(
                        R.styleable.SegmentedControlView_segment_tab_padding_vertical,
                        0f
                    );
                }
            } finally {
                attr.recycle()
            }
        }
    }

    fun initEnableTab(tabEnable : List<Boolean>){
        this.tabEnable.clear()
        this.tabEnable.addAll(tabEnable)
    }

    fun initTabs(
        iOnClickMenuTab: IMenuTabOnClickListener?,
        tabTiles: Array<String>
    ) {
        listener = iOnClickMenuTab
        if (tabEnable.isEmpty()) {
            repeat(tabTiles.count()) {
                tabEnable.add(true)
            }
        }

        for (i in tabTiles.indices) {
            val tabView: View = createTabView(i, tabTiles)
            tabView.setOnClickListener {
                if (currentTabIndex != i && tabEnable[i]) {
                    focusTab(i)
                    listener?.onClick(i)
                }
            }

            tabList.add(tabView)
            this.findViewById<LinearLayout>(R.id.tabs).addView(tabView)
        }

        focusTab(currentTabIndex)
    }

    private fun focusTab(tabIndex: Int) {
        focusTab(currentTabIndex, false)
        currentTabIndex = tabIndex
        focusTab(tabIndex, true)
    }

    private fun focusTab(tabIndex: Int, isFocus: Boolean) {
        val tabView = tabList[tabIndex]
        val tabTitle = tabView.findViewById<TextView>(R.id.tv_tab_title)
        if(tabEnable[tabIndex]){
            tabTitle.setTextAppearance(if (isFocus) R.style.FocusSegmentTab else R.style.UnFocusSegmentTab)
        }else{
            tabTitle.setTextAppearance(R.style.DisableSegmentTab)
        }
        val tabBackground = tabView.findViewById<View>(R.id.view_tab_background)
        tabTitle.setBackgroundColor(
            ContextCompat.getColor(
                context,
                if (isFocus) R.color.brand_orange_01 else R.color.base_gray_01
            )
        )
        val seperateLine = tabView.findViewById<View>(R.id.seperate_line)
//        seperateLine.visibility = if (!isFocus && tabIndex < tabList.size - 1) VISIBLE else GONE
    }

    private fun createTabView(index: Int, tabTiles: Array<String>): View {
        val tabView =
            LayoutInflater.from(context).inflate(R.layout.item_segment_tab, this, false)
        tabView.layoutParams = tabLayoutParams
        val tvTab = tabView.findViewById<TextView>(R.id.tv_tab_title)
        if(tabEnable[index]){
            tvTab.setTextAppearance(R.style.UnFocusSegmentTab)
        }else{
            tvTab.setTextAppearance(R.style.DisableSegmentTab)
        }
        tvTab.text = tabTiles[index]
        tvTab.setBackgroundColor( ContextCompat.getColor(
            context,
            R.color.base_gray_01
        ))
//        if (tabPaddingVertical > 0) {
//            tvTab.setPadding(0, tabPaddingVertical.toInt(), 0, tabPaddingVertical.toInt())
//        }
        val seperateLine = tabView.findViewById<View>(R.id.seperate_line)
        seperateLine.visibility = if (index == tabTiles.size - 1) GONE else VISIBLE
        return tabView
    }

    fun setPaddingTab(tabPaddingVertical : Int){
        this.tabPaddingVertical = tabPaddingVertical.toFloat()
    }

    fun getCurrentTabIndex() = currentTabIndex

    fun clearTabs() {
         tabList.clear()
        this.findViewById<LinearLayout>(R.id.tabs).removeAllViews()
    }
}
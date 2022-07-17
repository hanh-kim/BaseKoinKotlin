package com.hanhpk.basekoinkotlin.ui.customview

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import com.hanhpk.basekoinkotlin.R
import com.hanhpk.basekoinkotlin.databinding.ViewHeaderBinding
import com.hanhpk.basekoinkotlin.extensions.gone
import com.hanhpk.basekoinkotlin.extensions.onAvoidDoubleClick
import com.hanhpk.basekoinkotlin.extensions.visible

class HeaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    interface IOnMenuClickListener {
        fun onLeftMenuClick()
        fun onRightMenuClick()
    }

    var searchViewClick: () -> Unit = {}

    private var binding: ViewHeaderBinding =
        ViewHeaderBinding.inflate(LayoutInflater.from(context), this, false)

    var searchField: CustomSearchView? = binding.viewSearch
    var listener: IOnMenuClickListener? = null

    init {
//        val lp = binding.containerHeader.layoutParams
//        lp.height = resources.getDimensionPixelSize(R.dimen.height_top_bar) + DeviceUtil.getStatusBarHeight(context)
//        binding.containerHeader.setPadding(0,DeviceUtil.getStatusBarHeight(context),0,0)

        addView(binding.root)
        if (attrs != null) {
            val attr = context.obtainStyledAttributes(attrs, R.styleable.HeaderView)
            try {
                if (attr.hasValue(R.styleable.HeaderView_leftDrawable)) {
                    binding.btnMenuLeft.visibility = VISIBLE
                    binding.btnMenuLeft.setImageResource(
                        attr.getResourceId(
                            R.styleable.HeaderView_leftDrawable,
                            0
                        )
                    )
                } else {
                    binding.btnMenuLeft.visibility = GONE
                }

                if (attr.hasValue(R.styleable.HeaderView_leftTitle)) {
                    binding.tvMenuLeft.text = attr.getString(R.styleable.HeaderView_leftTitle)
                    binding.tvMenuLeft.visibility = VISIBLE
                    if(attr.hasValue(R.styleable.HeaderView_title_style)){
                        val style = attr.getResourceId(R.styleable.HeaderView_leftTitle_style,R.style.HeaderTitle)
                        binding.tvMenuLeft.setTextAppearance(style)
                    }
                } else {
                    binding.tvMenuLeft.visibility = GONE
                }

                if (attr.hasValue(R.styleable.HeaderView_search_view_click)) {
                    if (attr.getBoolean(R.styleable.HeaderView_search_view_click, false)) {
                        binding.viewSearch.setFocusEdittext()
                        binding.viewSearch.searchClick =  {
                            searchViewClick()
                        }
                    }
                }

                if (attr.hasValue(R.styleable.HeaderView_rightDrawable)) {
                    binding.btnMenuRight.visibility = VISIBLE
                    binding.btnMenuRight.setImageResource(
                        attr.getResourceId(
                            R.styleable.HeaderView_rightDrawable,
                            0
                        )
                    )
                } else {
                    binding.btnMenuRight.visibility = GONE
                }

                if (attr.hasValue(R.styleable.HeaderView_rightTitle)) {
                    binding.tvMenuRight.text =
                        attr.getString(R.styleable.HeaderView_rightTitle)
                    binding.tvMenuRight.visibility = VISIBLE
                    if(attr.hasValue(R.styleable.HeaderView_rightTitle_style)){
                        val style = attr.getResourceId(R.styleable.HeaderView_rightTitle_style,R.style.HeaderTitle)
                        binding.tvMenuRight.setTextAppearance(style)
                    }
                } else {
                    binding.tvMenuRight.visibility = GONE
                }

                if (attr.hasValue(R.styleable.HeaderView_title)) {
                    binding.tvHeaderTitle.visibility = VISIBLE
                    binding.tvHeaderTitle.text = attr.getText(R.styleable.HeaderView_title)
                } else {
                    binding.tvHeaderTitle.visibility = GONE
                }
//                if (attr.hasValue(R.styleable.HeaderView_fullScreen)) {
//                    val lp = binding.containerHeader.layoutParams
//                    if(attr.getBoolean(R.styleable.HeaderView_fullScreen,false)){
//                        lp.height = resources.getDimensionPixelSize(R.dimen.height_top_bar) + DeviceUtil.getStatusBarHeight(context)
//                        binding.containerHeader.setPadding(0,DeviceUtil.getStatusBarHeight(context),0,0)
//                    }else{
//                        lp.height = resources.getDimensionPixelSize(R.dimen.height_top_bar)
//                    }
//                }

                if(attr.hasValue(R.styleable.HeaderView_title_style)){
                    val style = attr.getResourceId(R.styleable.HeaderView_title_style,R.style.HeaderTitle)
                    binding.tvHeaderTitle.setTextAppearance(style)
                }

                if(attr.hasValue(R.styleable.HeaderView_view_margin)){
                    var viewMargin =  attr.getDimension(R.styleable.HeaderView_view_margin, 0f)
                    val lpViewSegment = binding.containerHeader.layoutParams as LayoutParams
                    lpViewSegment.bottomMargin = viewMargin.toInt()
                    binding.containerHeader.layoutParams = lpViewSegment
                }

                if (attr.getBoolean(R.styleable.HeaderView_title_marquee, false)) {
                    binding.tvHeaderTitle.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
                        marginStart = resources.getDimensionPixelSize(R.dimen.dimen_12dp)
                        marginEnd = resources.getDimensionPixelSize(R.dimen.dimen_12dp)
                        gravity = Gravity.CENTER
                    }

                    val set = ConstraintSet()
                    set.clone(binding.containerHeader)
                    set.connect(binding.tvHeaderTitle.id, ConstraintLayout.LayoutParams.START,
                        binding.guideLeft.id, ConstraintLayout.LayoutParams.END)
                    set.connect(binding.tvHeaderTitle.id, ConstraintLayout.LayoutParams.END,
                        binding.guideRight.id, ConstraintLayout.LayoutParams.START)
                    set.applyTo(binding.containerHeader)

                    binding.tvHeaderTitle.ellipsize = TextUtils.TruncateAt.MARQUEE
                    binding.tvHeaderTitle.focusable = FOCUSABLE
                    binding.tvHeaderTitle.isFocusableInTouchMode = true
                    binding.tvHeaderTitle.isFocusedByDefault = true
                    binding.tvHeaderTitle.marqueeRepeatLimit = -1
                    binding.tvHeaderTitle.setSingleLine()
                    binding.tvHeaderTitle.isSelected = true
                    binding.tvHeaderTitle.alwaysFocused = true
                    binding.tvHeaderTitle.requestFocus()
                }

                binding.viewSearch.visibility =
                    if (attr.hasValue(R.styleable.HeaderView_action) && attr.getString(R.styleable.HeaderView_action)
                            .equals("search")
                    ) VISIBLE else GONE

                // Click listener
                binding.btnMenuLeft.onAvoidDoubleClick { listener?.onLeftMenuClick() }
                binding.tvMenuLeft.onAvoidDoubleClick { listener?.onLeftMenuClick() }
                binding.btnMenuRight.onAvoidDoubleClick { listener?.onRightMenuClick() }
                binding.tvMenuRight.onAvoidDoubleClick { listener?.onRightMenuClick() }
            } finally {
                attr.recycle()
            }
        }
    }

    fun setHeaderBackgroundColor(color: Int){
        binding.containerHeader.setBackgroundColor(color)
    }

    fun setHeaderGradientBackgroundColor(){
        val frameColor = resources.getIntArray(R.array.gradient)
        val rainbow = GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            frameColor
        ).apply {
            gradientType = GradientDrawable.LINEAR_GRADIENT
        }
        binding.containerHeader.background = (rainbow)
    }

    fun setTitleBackground(drawable:Int){
        binding.tvHeaderTitle.setBackgroundResource(drawable)
    }

    fun setTitle(title:String){
        binding.tvHeaderTitle.text = title
    }

    fun setLeftDrawable(@DrawableRes id: Int) {
        binding.btnMenuLeft.visibility = VISIBLE
        binding.btnMenuLeft.setImageResource(id)
        binding.btnMenuRight.visibility = GONE
    }

    fun setRightDrawable(@DrawableRes id: Int) {
        binding.btnMenuRight.visibility = VISIBLE
        binding.btnMenuRight.setImageResource(id)
    }

    fun showLeftDrawable(isShow : Boolean){
        if(isShow){
            binding.btnMenuLeft.visible()
            binding.btnMenuRight.gone()
        }else{
            binding.btnMenuLeft.gone()
            binding.btnMenuRight.visible()
        }
    }

    fun setLeftTitle(@StringRes id: Int) {
        binding.tvMenuLeft.visible()
        binding.tvMenuLeft.text = context.getString(id)
        binding.btnMenuLeft.gone()
    }

    fun showRightTitle(isShow: Boolean = true){
        if(isShow){
            binding.tvMenuRight.visible()
        }else{
            binding.tvMenuRight.gone()
        }
    }

    fun isButtonClose(isClose: Boolean) {
        if (isClose) {
            binding.btnMenuRight.visible()
            binding.btnMenuLeft.gone()
        }
        binding.btnMenuRight.setImageResource(if (isClose) R.drawable.btn_close else R.drawable.btn_circle_back)
    }

    fun setEnableRightTitle(enable: Boolean){
        binding.tvMenuRight.isEnabled = enable
        if (enable){
            binding.tvMenuRight.setTextColor(context.getColor(R.color.white))

        }else{
            binding.tvMenuRight.setTextColor(context.getColor(R.color.overlay_white))
        }
    }

//    fun setFullScreenHeader(isFullScreen : Boolean){
//        val lp = binding.containerHeader.layoutParams
//        if(isFullScreen){
//            lp.height = resources.getDimensionPixelSize(R.dimen.height_top_bar) + DeviceUtil.getStatusBarHeight(context)
//            binding.containerHeader.setPadding(0,DeviceUtil.getStatusBarHeight(context),0,0)
//        }else{
//            lp.height = resources.getDimensionPixelSize(R.dimen.height_top_bar)
//            binding.containerHeader.setPadding(0,0,0,0)
//        }
//    }
}
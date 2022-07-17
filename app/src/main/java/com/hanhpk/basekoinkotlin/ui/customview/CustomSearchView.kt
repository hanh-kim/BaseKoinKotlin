package com.hanhpk.basekoinkotlin.ui.customview

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import com.hanhpk.basekoinkotlin.R
import com.hanhpk.basekoinkotlin.databinding.ViewSearchFieldBinding
import com.hanhpk.basekoinkotlin.extensions.hideKeyboard
import com.hanhpk.basekoinkotlin.extensions.observableFromView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CustomSearchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    interface IOnSearchListener {
        fun onSearch(value: String)
    }

    var searchClick: () -> Unit = {}

    var listener: IOnSearchListener? = null
    private var binding: ViewSearchFieldBinding =
        ViewSearchFieldBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        addView(binding.root)

        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomSearchView)

        try {
            if(a.hasValue(R.styleable.CustomSearchView_search_view_background)){
                val background = a.getDrawable(R.styleable.CustomSearchView_search_view_background)
                background?.let {
                    binding.edtSearchField.background = it
                }
            }
            if(a.hasValue(R.styleable.CustomSearchView_search_view_padding)){
                val background = a.getDimension(R.styleable.CustomSearchView_search_view_padding, 0f)
                background?.let {
                    binding.edtSearchField.setPadding(it.toInt(), it.toInt(), it.toInt(), it.toInt())
                }
            }
            val hint = a.getString(R.styleable.CustomSearchView_search_view_hint)
            hint?.let {
                binding.edtSearchField.hint = it
            }
        } finally {
            a.recycle()
        }

        binding.edtSearchField.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(textView)
            }
            true
        }

        binding.edtSearchField.observableFromView()
            .debounce(100, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isEmpty()) {
                    binding.viewClearSearch.visibility = View.GONE
                } else {
                    binding.viewClearSearch.visibility = View.VISIBLE
                }
                onAfterTextChanged(it)
            }

        binding.viewClearSearch.setOnClickListener {
            binding.edtSearchField.text = null
        }
    }

    private fun performSearch(textView: TextView) {
        textView.text?.toString()?.let { it1 -> listener?.onSearch(it1) }
        binding.edtSearchField.hideKeyboard()
    }

    fun getText(): String{
       return binding.edtSearchField.text.toString()
    }

    fun setHint(hint: String?){
        binding.edtSearchField.hint = hint
    }

    fun setFocusEdittext() {
        binding.edtSearchField.isFocusable = false
        binding.edtSearchField.inputType = InputType.TYPE_NULL
        binding.edtSearchField.setOnTouchListener { p0, p1 ->
            if (p1?.action == MotionEvent.ACTION_UP) {
                searchClick()
            }
            false
        }
    }

    fun resetText() {
        binding.edtSearchField.setText("")
    }

    fun setText(value: String) {
        binding.edtSearchField.setText(value)
    }

    var onAfterTextChanged: (key: String) -> Unit = {}
}
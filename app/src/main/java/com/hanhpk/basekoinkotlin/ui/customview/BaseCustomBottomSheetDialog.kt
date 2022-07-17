package com.hanhpk.basekoinkotlin.ui.customview

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.NonNull
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hanhpk.basekoinkotlin.R

abstract class BaseCustomBottomSheetDialog<B : ViewBinding>  : BottomSheetDialogFragment() {
    private var _binding: B? = null
    val binding get() = _binding!!

    override fun getTheme(): Int {
        return R.style.SortOptionBottomSheetDialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getDialogBinding(inflater, container)
        initView(inflater, container, binding)
        return binding.root
    }

    abstract fun initView(inflater: LayoutInflater, container: ViewGroup?, binding: B)
    abstract fun getDialogBinding(inflater: LayoutInflater, container: ViewGroup?): B

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireActivity(), theme) {
            override fun dispatchTouchEvent(@NonNull motionEvent: MotionEvent): Boolean {
                if (currentFocus != null) {
                    val inputMethodManager: InputMethodManager =
                        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                }
                return super.dispatchTouchEvent(motionEvent)
            }
        }

    }
}
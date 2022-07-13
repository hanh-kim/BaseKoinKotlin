package com.hanhpk.basekoinkotlin.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.hanhpk.basekoinkotlin.R

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    private var _binding: T? = null

    val binding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = initBinding(inflater, container)
        initViews(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initListeners()
    }

    abstract fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): T

    abstract fun initViews(viewBinding: T)
    abstract fun initData()
    abstract fun initListeners()

    fun transitionFragment(
        fragment: BaseFragment<*>,
        @IdRes layoutId: Int,
        args: Bundle? = null,
        @AnimRes slideEnter: Int= R.anim.anim_slide_right_to_left,
        @AnimRes slideExit: Int= R.anim.anim_slide_left_to_right,
        @AnimRes popEnter: Int= R.anim.anim_slide_right_to_left,
        @AnimRes popExit: Int= R.anim.anim_slide_left_to_right,
    ) {
        val fragmentManager = activity?.supportFragmentManager
        fragment.arguments = args
        fragmentManager?.beginTransaction()
            ?.setCustomAnimations(slideEnter,slideExit, popEnter, popExit)
            ?.add(layoutId, fragment)
            ?.addToBackStack(fragment.tag)
            ?.commit()
    }

    fun transitionFragmentNoAnimation(
        fragment: BaseFragment<*>,
        @IdRes layoutId: Int,
        args: Bundle? = null
    ) {
        val fragmentManager = activity?.supportFragmentManager
        fragment.arguments = args
        fragmentManager?.beginTransaction()
            ?.add(layoutId, fragment)
            ?.addToBackStack(fragment.tag)
            ?.commit()
    }

    fun replaceFragmentNoAnimation(
        fragment: BaseFragment<*>,
        @IdRes layoutId: Int,
        args: Bundle? = null
    ) {
        val fragmentManager = activity?.supportFragmentManager
        fragment.arguments = args
        fragmentManager?.beginTransaction()
            ?.replace(layoutId, fragment)
            ?.commit()
    }

    fun replaceFragment(
        fragment: BaseFragment<*>,
        @IdRes layoutId: Int,
        args: Bundle? = null,
        @AnimRes slideEnter: Int= R.anim.anim_slide_right_to_left,
        @AnimRes slideExit: Int= R.anim.anim_slide_left_to_right,
        @AnimRes popEnter: Int= R.anim.anim_slide_right_to_left,
        @AnimRes popExit: Int= R.anim.anim_slide_left_to_right,
    ) {
        val fragmentManager = activity?.supportFragmentManager
        fragment.arguments = args
        fragmentManager?.beginTransaction()
            ?.setCustomAnimations(slideEnter,slideExit, popEnter, popExit)
            ?.replace(layoutId, fragment)
            ?.commit()
    }

    fun showProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
        (context as Activity).window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun hideProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.GONE
        (context as Activity).window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun onDestroy() {
        super.onDestroy()
        (context as? Activity)?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

}
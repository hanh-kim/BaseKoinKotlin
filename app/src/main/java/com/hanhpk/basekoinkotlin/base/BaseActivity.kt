package com.hanhpk.basekoinkotlin.base

import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import android.os.PersistableBundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.hanhpk.basekoinkotlin.R
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseActivity<B:ViewDataBinding, VM:BaseViewModel> : AppCompatActivity() {

    private var _binding: B? = null

    abstract val viewModel: VM

    protected val binding = _binding!!

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        _binding = DataBindingUtil.setContentView(this,layoutId())
        initViews(binding)
    }

    abstract fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): B
    abstract fun initViews(binding: B)

    abstract fun layoutId():Int

    fun transitionFragment(
        fragment: BaseFragment<*>,
        @IdRes layoutId: Int,
        args: Bundle? = null,
        @AnimRes slideEnter: Int= R.anim.anim_slide_right_to_left,
        @AnimRes slideExit: Int= R.anim.anim_slide_left_to_right,
        @AnimRes popEnter: Int= R.anim.anim_slide_right_to_left,
        @AnimRes popExit: Int= R.anim.anim_slide_left_to_right,
    ) {
        val fragmentManager = supportFragmentManager
        fragment.arguments = args
        fragmentManager.beginTransaction()
            .setCustomAnimations(slideEnter,slideExit, popEnter, popExit)
            .add(layoutId, fragment)
            .addToBackStack(fragment.tag)
            .commit()
    }

    fun transitionFragmentNoAnimation(
        fragment: BaseFragment<*>,
        @IdRes layoutId: Int,
        args: Bundle? = null
    ) {
        val fragmentManager = supportFragmentManager
        fragment.arguments = args
        fragmentManager.beginTransaction()
            .add(layoutId, fragment)
            .addToBackStack(fragment.tag)
            .commit()
    }

    fun replaceFragmentNoAnimation(
        fragment: BaseFragment<*>,
        @IdRes layoutId: Int,
        args: Bundle? = null
    ) {
        val fragmentManager = supportFragmentManager
        fragment.arguments = args
        fragmentManager.beginTransaction()
            .replace(layoutId, fragment)
            .commit()
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
        val fragmentManager = supportFragmentManager
        fragment.arguments = args
        fragmentManager.beginTransaction()
            .setCustomAnimations(slideEnter,slideExit, popEnter, popExit)
            .replace(layoutId, fragment)
            .commit()
    }

    fun showProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun hideProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val focusView = currentFocus
        if (focusView != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev!!.x.toInt()
            val y = ev.y.toInt()
            if (!rect.contains(x, y)) {
                val imm: InputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                if (imm != null) imm.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onDestroy() {
        super.onDestroy()
        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}
package com.hanhpk.basekoinkotlin.common

import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.widget.OverScroller
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout

// AppBar部分を上方向フリックするとスクロールが途中で止まる問題の対処用Behavior。
// RecyclerView部分であれば問題なくスクロールできるため、
// AppBarのフリックを奪い取り、同じ速度でRecyclerViewをフリックした扱いにする。
// ref: https://stackoverflow.com/a/57441618
class SmoothAppBarBehavior : AppBarLayout.Behavior() {

    companion object {
        private const val FLING_UNITS = 1000 //copied from base class
    }

    var recyclerView: RecyclerView? = null
    private var overScroller: OverScroller? = null
    private var pointerId = -1
    private var velocityTracker: VelocityTracker? = null

    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        stopAppBarLayoutFling()
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        ev: MotionEvent
    ): Boolean {
        val consumed = super.onInterceptTouchEvent(parent, child, ev)
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                ensureVelocityTracker()
                recyclerView?.stopScroll()
                pointerId = ev.getPointerId(0)
            }
            MotionEvent.ACTION_CANCEL -> {
                velocityTracker?.recycle()
                velocityTracker = null
                pointerId = -1
            }
            else -> {}
        }
        return consumed
    }

    override fun onTouchEvent(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        ev: MotionEvent
    ): Boolean {
        val consumed = super.onTouchEvent(parent, child, ev)
        recyclerView?.let {
            when (ev.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    ensureVelocityTracker()
                    pointerId = ev.getPointerId(0)
                }
                MotionEvent.ACTION_UP -> {
                    stopAppBarLayoutFling()
                    recyclerView?.fling(0, getYVelocity(ev))
                }
                MotionEvent.ACTION_CANCEL -> {
                    velocityTracker?.recycle()
                    velocityTracker = null
                    pointerId = -1
                }
                else -> {}
            }
            velocityTracker?.addMovement(ev)
        }
        return consumed
    }

    private fun ensureVelocityTracker() {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        }
    }

    private fun getYVelocity(event: MotionEvent): Int {
        velocityTracker?.let {
            it.addMovement(event)
            it.computeCurrentVelocity(FLING_UNITS)
            return -it.getYVelocity(pointerId).toInt()
        }
        return 0
    }

    private fun stopAppBarLayoutFling() {
        if (overScroller == null) {
            val scrollerField =
                javaClass.superclass.superclass.superclass.getDeclaredField("scroller")
            scrollerField.isAccessible = true
            overScroller = scrollerField.get(this) as? OverScroller
        }
        overScroller?.forceFinished(true)
    }
}

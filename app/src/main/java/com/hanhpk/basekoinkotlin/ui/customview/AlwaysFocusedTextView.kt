package com.hanhpk.basekoinkotlin.ui.customview

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


// TextViewのellipsize="marquee"はフォーカスが当たっていないと動作しない仕様
// ref: https://stackoverflow.com/a/2504840
class AlwaysFocusedTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatTextView(context, attrs, defStyleAttr) {

    var alwaysFocused = false

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        if (alwaysFocused) {
            if (focused) {
                super.onFocusChanged(focused, direction, previouslyFocusedRect)
            }
        } else {
            super.onFocusChanged(focused, direction, previouslyFocusedRect)
        }
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        if (alwaysFocused) {
            if (hasWindowFocus) {
                super.onWindowFocusChanged(hasWindowFocus)
            }
        } else {
            super.onWindowFocusChanged(hasWindowFocus)
        }
    }

    override fun isFocused(): Boolean {
        return if (alwaysFocused) {
            true
        } else {
            super.isFocused()
        }
    }
}
package com.hanhpk.basekoinkotlin.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.VibrationEffect

import android.os.Build
import android.os.Vibrator
import android.util.Base64
import android.view.inputmethod.InputMethodManager
import java.io.ByteArrayOutputStream


object DeviceUtil {
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId: Int = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
    fun getNavigationBarHeight(context: Context): Int {
        var result = 0
        val resourceId: Int = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun convertToDp(context: Context,size: Int): Int = (size * context.resources.displayMetrics.density).toInt()

    fun vibrate(context: Context){
        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v?.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            //deprecated in API 26
            v?.vibrate(500)
        }
    }

    fun hideSoftKeyboard(activity: Activity?) {
        if (activity != null && activity.window != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }

    fun convertToBase64(context: Context, imgDrawable: Int): String {
        val bitmap = BitmapFactory.decodeResource(context.resources, imgDrawable)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val bitMapData = stream.toByteArray()

        val encodedImage: String = Base64.encodeToString(bitMapData, Base64.DEFAULT)
        return encodedImage
    }

    fun heightScreenPixel(context: Context?): Int {
        if (context == null) {
            return 0
        }
        val resources = context.resources
        val metrics = resources.displayMetrics

        return metrics.heightPixels
    }
}
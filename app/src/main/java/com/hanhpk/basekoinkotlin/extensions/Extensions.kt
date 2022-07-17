package com.hanhpk.basekoinkotlin.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Rect
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextPaint
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.hanhpk.basekoinkotlin.utils.DateUtils
import com.hanhpk.basekoinkotlin.utils.DateUtils.FORMAT_YEAR_MONTH_DAY_HYPHEN
import com.google.android.material.appbar.AppBarLayout
import com.hanhpk.basekoinkotlin.AndroidApplication
import com.hanhpk.basekoinkotlin.R
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.text.SimpleDateFormat
import java.util.*

const val MY_PERMISSIONS_REQUEST_LOCATION = 111

const val CLICK_THROTTLE_DELAY = 800L

fun View.hideKeyboard() {
    val imm =
        this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun Fragment.showSoftKeyboard() {
    val imm = this.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun ViewGroup.addViewObserver(function: () -> Unit) {
    val view = this
    view.viewTreeObserver.addOnGlobalLayoutListener(object :
        ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            view.viewTreeObserver.removeOnGlobalLayoutListener(this)
            function.invoke()
        }
    })
}

fun TextView.getBoundWidth(paint: TextPaint): Int {
    val bounds = Rect()
    paint.getTextBounds(this.text.toString(), 0, this.text.length, bounds)
    val width: Int = bounds.width()
    return width
}

fun Context.openActivityBySchemeUrl(schemeUrl: String, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(schemeUrl))
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun Activity.openActivityBySchemeUrlTransition(schemeUrl: String, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(schemeUrl))
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
    overridePendingTransition(R.anim.slide_up_activity, R.anim.nothing)
}

fun Activity.overridePendingTransitionBack() {
    overridePendingTransition(R.anim.nothing, R.anim.slide_down_activity)
}

fun Activity.overridePendingTransition() {
    overridePendingTransition(R.anim.slide_up_activity, R.anim.nothing)
}

fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun <T> Context.openActivityNoAnimation(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
    startActivity(intent)
}

fun <T> Context.openActivityForResult(it: Class<T>, extras: Bundle.() -> Unit = {}): Intent {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))

    return intent
}

fun Context.openActivityBySchemeUrlForResult(
    schemeUrl: String,
    extras: Bundle.() -> Unit = {}
): Intent {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(schemeUrl))
    intent.putExtras(Bundle().apply(extras))

    return intent
}

fun String.convertToDate(formatDate: String, locale: Locale = Locale.JAPAN): Date? {
    try {
        return SimpleDateFormat(formatDate, locale).parse(this)
    } catch (ex: Exception) {
    }
    return null
}

fun Date.convertToString(formatDate: String, locale: Locale = Locale.JAPAN): String {
    return SimpleDateFormat(formatDate, locale).format(this)
}

fun String.convertStringToMillisecond(formatDate: String, locale: Locale = Locale.JAPAN): Long {
    return try {
        SimpleDateFormat(formatDate, locale).parse(this).time
    } catch (ex: Exception) {
        0
    }
}

fun Long.convertTimeToString(formatDate: String, locale: Locale = Locale.JAPAN): String {
    val date = Date(this)
    val format = SimpleDateFormat(formatDate, locale)
    return format.format(date)
}


fun Calendar.calToCode(): String {
    return calendarToFormatApi(DateUtils.FORMAT_YEAR_MONTH_DAY_CODE)
}

fun View.slideUp() {
    val animation: Animation = AnimationUtils.loadAnimation(
        AndroidApplication.mInstance,
        R.anim.anim_slide_up
    )
    this.animation = animation
}

fun View.slideDown() {
    val animation: Animation = AnimationUtils.loadAnimation(
        AndroidApplication.mInstance,
        R.anim.anim_slide_down
    )
    this.animation = animation
}

fun View.slideLeftToRight() {
    val animation: Animation = AnimationUtils.loadAnimation(
        AndroidApplication.mInstance,
        R.anim.anim_slide_left_to_right
    )
    this.animation = animation
}

fun View.slideRightToLeft() {
    val animation: Animation = AnimationUtils.loadAnimation(
        AndroidApplication.mInstance,
        R.anim.anim_slide_left_to_right
    )
    this.animation = animation
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

@ColorInt
fun Context.color(@ColorRes res: Int): Int {
    return ContextCompat.getColor(this, res)
}

fun Activity.checkLocationPermission(): Boolean {
    return ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

fun Activity.requestLocationPermission() {
    ActivityCompat.requestPermissions(
        this,
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
        ),
        MY_PERMISSIONS_REQUEST_LOCATION
    )
}

fun Double.format(digits: Int) = "%.${digits}f".format(Locale.JAPAN, this)

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun RatingBar.onRatingBarChanged(ratingBarChanged: (Float) -> Unit) {
    this.setOnRatingBarChangeListener { ratingBar, fl, b ->
        ratingBarChanged.invoke(fl)
    }
}

fun Activity.hideSoftKeyboard() {
    if (window != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }
}

fun Context.openUrl(url: String?) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(intent)
}

fun Activity.lockScreenOrientation() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
}

fun Activity.unlockScreenOrientation() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
}

fun AppCompatImageView.loadImageUrlWithDefault(url: Any, @DrawableRes placeHolder: Int) {
    Glide.with(this.context)
        .load(url)
        .error(placeHolder)
        .placeholder(placeHolder)
        .into(this)
}

fun ViewPager2.reduceDragSensitivity() {
    val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
    recyclerViewField.isAccessible = true
    val recyclerView = recyclerViewField.get(this) as RecyclerView

    val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
    touchSlopField.isAccessible = true
    val touchSlop = touchSlopField.get(recyclerView) as Int
    touchSlopField.set(recyclerView, touchSlop * 4)
}

fun AppCompatActivity.enableFullScreen() {
    val decorView = window.decorView
    decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
}

fun AppBarLayout.LayoutParams.enableAppBarScroll(isEnable: Boolean) {
    if (isEnable) {
        this.scrollFlags =
            AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
    } else {
        this.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
    }
}

fun AppBarLayout.LayoutParams.enableAppBarScrollEnterAlways(isEnable: Boolean) {
    if (isEnable) {
        this.scrollFlags =
            AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
    } else {
        this.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
    }
}

fun Context.goToMapApp(lat: Double, lng: Double) {
    val mapId: ArrayList<String> = arrayListOf(
        "com.google.android.apps.maps",
        "com.google.android.apps.mapslite"
    )
    val uriStr = String.format(
        Locale.ENGLISH,
        "geo:%f,%f?z=17&q=%f,%f",
        lat,
        lng,
        lat,
        lng
    )
    var countNoApp = 0
    kotlin.run loop@{
        mapId.forEach {
            val gmmIntentUri = Uri.parse(uriStr)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage(it)
            mapIntent.resolveActivity(packageManager)?.let {
                startActivity(mapIntent)
                return@loop
            } ?: kotlin.run {
                countNoApp++
            }
        }
    }

    if (countNoApp == mapId.size) {
        val uri = java.lang.String.format(
            Locale.ENGLISH,
            "https://www.google.com/maps/dir/%.7f,%.7f,15z",
            lat,
            lng
        )
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }
}

fun String.stringDateToCalendar(format: String = FORMAT_YEAR_MONTH_DAY_HYPHEN): Calendar {
    val spf = SimpleDateFormat(format, Locale.JAPAN)
    return try {
        val cal = Calendar.getInstance()
        cal.time = spf.parse(this)
        cal
    } catch (e: Exception) {
        Calendar.getInstance()
    }
}

fun Context.isLocationEnabled(): Boolean {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return LocationManagerCompat.isLocationEnabled(locationManager)
}

fun Calendar.calendarToFormatApi(format: String): String {
    val sdf = SimpleDateFormat(format, Locale.JAPAN)
    return sdf.format(this.time)
}

fun String.appendZeroPrefix(): String {
    return if (this.length == 1) "0${this}"
    else this
}

fun Date.getDayOfWeekInJp(): String {
    val jpWeekDays: Array<String> =
        AndroidApplication.mInstance.resources.getStringArray(R.array.jp_week_days)
    val c = Calendar.getInstance()
    c.time = this
    val day = c[Calendar.DAY_OF_WEEK]
    return jpWeekDays[day - 1]
}


fun View.onAvoidDoubleClick(delayInMillis: Long = 800L, action: () -> Unit) {
    setOnClickListener {
        action()
        this.isClickable = false
        this.postDelayed({ isClickable = true }, delayInMillis)
    }
}

//EditTextExt
fun EditText.observableFromView(): Observable<String> {
    val subject = PublishSubject.create<String>()
    onTextChanged {
        subject.onNext(it)
    }
    return subject
}

fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged.invoke(s.toString())
        }

        override fun afterTextChanged(editable: Editable?) {
        }
    })
}

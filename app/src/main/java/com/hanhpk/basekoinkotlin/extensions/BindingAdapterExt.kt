package com.hanhpk.basekoinkotlin.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter


@BindingAdapter("src_image")
fun setImageSrc(view: ImageView,@DrawableRes src: Int?) {
    src?.let { view.loadImage(it) }
}

@BindingAdapter("url_image")
fun setImageUrl(view: ImageView, url: String?) {
    url?.let { view.loadImage(it) }
}

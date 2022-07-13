package com.hanhpk.basekoinkotlin.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ImageView.loadImage(url:Any){
    Glide.with(this)
        .load(url)
        .into(this)
}

fun ImageView.loadImageAndDeleteCache(url:Any){
    Glide.with(this)
        .load(url)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)
}
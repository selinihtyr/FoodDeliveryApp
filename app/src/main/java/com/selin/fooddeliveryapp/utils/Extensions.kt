package com.selin.fooddeliveryapp.utils

import android.util.Size
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(imageUrl: String, size: com.selin.fooddeliveryapp.utils.Size? = null){
    var requestOptions = RequestOptions()

    if(size != null){
        requestOptions = requestOptions.override(size.width, size.height)
    }

    Glide.with(this.context)
        .load(imageUrl)
        .apply(requestOptions)
        .into(this)
}

fun String?.toSafeInt(): Int {
    return this?.toIntOrNull() ?: 0
}

data class Size(val width: Int, val height: Int)
package com.happinessinc.getshwifty.utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.happinessinc.getshwifty.R
import java.io.File

@BindingAdapter("rect_image_Url")
fun loadRectImage(imageView: AppCompatImageView, url: String?) {
    Glide.with(imageView.getContext())
        .load(url)
        .override(80,80)
        .placeholder(R.drawable.ic_place_holder)
        .into(imageView)
}

@BindingAdapter("load_large")
fun loadLarge(imageView: AppCompatImageView, url: String?) {
    Glide.with(imageView.getContext())
        .load(url)
        .placeholder(R.drawable.ic_place_holder)
        .into(imageView)
}
@BindingAdapter("circle_image_Url")
fun loadCircleImage(imageView: AppCompatImageView, url: String?) {
    Glide.with(imageView.getContext())
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .placeholder(R.drawable.ic_place_holder)
        .into(imageView)
}
@BindingAdapter("doc_Url")
fun loadDoc(imageView: AppCompatImageView, url: String?) {
    Glide.with(imageView.getContext())
        .load(url)
        .placeholder(R.drawable.ic_place_holder)
        .into(imageView)
}


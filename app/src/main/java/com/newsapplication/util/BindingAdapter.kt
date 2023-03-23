package com.newsapplication.util

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun setImageUrl(imgView: AppCompatImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide.with(imgView.context)
            .load(imgUrl)
            .placeholder(android.R.drawable.ic_menu_report_image)
            .error(android.R.drawable.ic_menu_report_image)
            .into(imgView)
    }
}

@BindingAdapter("loadUrl")
fun WebView.setUrl(content: String) {
    this.webViewClient = WebViewClient()
    this.settings.javaScriptEnabled = true
    this.settings.allowContentAccess = true
    this.settings.domStorageEnabled = true
    this.loadData(content, "text/html", "utf-8")
}
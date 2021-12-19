package com.tejas.relifemedicalsystemtest.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tejas.relifemedicalsystemtest.R
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {

    @BindingAdapter("articleDate")
    @JvmStatic
    fun articleDate(textView: TextView?, data: String?) {
        textView?.apply {
            if (data.isNullOrEmpty()) {
                text = ""
                return
            }
            text = formatDate(data)
        }
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String?) {
        view.apply {
            Glide.with(view.context)
                .load(url)
                .placeholder(R.drawable.ic_heartbeat)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view)
        }
    }

    private fun formatDate(input: String?): String {
        return try {
            if (input.isNullOrEmpty()) {
                return ""
            }
            val sdf = SimpleDateFormat(Constants.inputPattern, Locale.getDefault())
            val date = sdf.parse(input)
            sdf.applyPattern(Constants.outputPattern)
            sdf.format(date ?: Date())
        } catch (e: Exception) {
            Timber.e(e)
            ""
        }
    }
}
package com.tejas.relifemedicalsystemtest.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
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
            ""
        }
    }
}
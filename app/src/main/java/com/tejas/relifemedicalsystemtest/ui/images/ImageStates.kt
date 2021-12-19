package com.tejas.relifemedicalsystemtest.ui.images

import com.tejas.relifemedicalsystemtest.network.ImageResponse

data class ImageResult(
    var isLoading: Boolean,
    val success: Boolean = false,
    val response: ImageResponse? = null,
    val message: String? = "",
)
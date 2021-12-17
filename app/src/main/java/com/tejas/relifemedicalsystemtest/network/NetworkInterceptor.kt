package com.tejas.relifemedicalsystemtest.network

import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val modifiedRequest = chain.request().newBuilder()
            .addHeader("User-Agent", "android")
        return chain.proceed(modifiedRequest.build())
    }
}
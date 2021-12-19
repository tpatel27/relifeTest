package com.tejas.relifemedicalsystemtest.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImageBaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseRetrofitInstance

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImageRetrofitInstance
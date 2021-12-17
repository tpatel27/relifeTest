package com.tejas.relifemedicalsystemtest.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tejas.relifemedicalsystemtest.BuildConfig
import com.tejas.relifemedicalsystemtest.network.NetworkInterceptor
import com.tejas.relifemedicalsystemtest.network.SpaceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun providesGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun providesDataRequestInterceptor() = HttpLoggingInterceptor { message ->
        Timber.i(message)
    }.apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
    }

    @Singleton
    @Provides
    fun provideSupportClient(
        networkInterceptor: NetworkInterceptor,
        dataRequestInterceptor: HttpLoggingInterceptor,
    ) = OkHttpClient()
        .newBuilder()
        .addInterceptor(networkInterceptor)
        .addInterceptor(dataRequestInterceptor)
        .connectTimeout(1, TimeUnit.MINUTES)
        .callTimeout(1, TimeUnit.MINUTES)
        .build()

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideGsonFactory(
        gson: Gson?
    ): GsonConverterFactory = GsonConverterFactory.create(gson ?: Gson())

    @Singleton
    @Provides
    fun provideNetworkBuilder(
        baseUrl: String,
        supportClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(gsonConverterFactory)
        .client(supportClient)
        .build()

    @Singleton
    @Provides
    fun provideNetworkInstance(
        networkBuilder: Retrofit
    ): SpaceApi = networkBuilder.create(SpaceApi::class.java)
}
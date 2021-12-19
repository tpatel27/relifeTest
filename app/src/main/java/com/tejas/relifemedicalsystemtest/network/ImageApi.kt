package com.tejas.relifemedicalsystemtest.network

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PicApi {

    @GET("api/")
    suspend fun getImages(
        @Query("key") apiKey: String? = "",
        @Query("q") query: String? = "",
        @Query("image_type") type: String? = "photo",
    ): Response<ImageResponse>
}

data class ImageResponse(

    @field:SerializedName("hits")
    val hits: List<HitsItem> = mutableListOf(),

    @field:SerializedName("total")
    val total: Int? = -1,

    @field:SerializedName("totalHits")
    val totalHits: Int? = -1
)

data class HitsItem(

    @field:SerializedName("webformatHeight")
    val webFormatHeight: Int? = -1,

    @field:SerializedName("imageWidth")
    val imageWidth: Int? = -1,

    @field:SerializedName("previewHeight")
    val previewHeight: Int? = -1,

    @field:SerializedName("webformatURL")
    val webFormatURL: String? = "",

    @field:SerializedName("userImageURL")
    val userImageURL: String? = "",

    @field:SerializedName("previewURL")
    val previewURL: String? = "",

    @field:SerializedName("comments")
    val comments: Int? = -1,

    @field:SerializedName("type")
    val type: String? = "",

    @field:SerializedName("imageHeight")
    val imageHeight: Int? = -1,

    @field:SerializedName("tags")
    val tags: String? = "",

    @field:SerializedName("previewWidth")
    val previewWidth: Int? = -1,

    @field:SerializedName("downloads")
    val downloads: Int? = -1,

    @field:SerializedName("collections")
    val collections: Int? = -1,

    @field:SerializedName("user_id")
    val userId: Int? = -1,

    @field:SerializedName("largeImageURL")
    val largeImageURL: String? = "",

    @field:SerializedName("pageURL")
    val pageURL: String? = "",

    @field:SerializedName("id")
    val id: Int? = -1,

    @field:SerializedName("imageSize")
    val imageSize: Int? = -1,

    @field:SerializedName("webformatWidth")
    val webFormatWidth: Int? = -1,

    @field:SerializedName("user")
    val user: String? = "",

    @field:SerializedName("views")
    val views: Int? = -1,

    @field:SerializedName("likes")
    val likes: Int? = -1
)
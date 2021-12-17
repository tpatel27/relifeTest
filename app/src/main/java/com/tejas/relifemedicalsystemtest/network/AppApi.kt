package com.tejas.relifemedicalsystemtest.network

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceApi {

    @GET("v3/articles")
    suspend fun getArticleList(): Response<MutableList<SingleArticleResponse>>

    @GET("v3/articles/{id}")
    suspend fun getSingleArticle(
        @Path("id") id: Int,
    ): Response<SingleArticleResponse>
}

//data class ArticleResponse(
//
//    @field:SerializedName("ArticleResponse")
//    val articleResponse: MutableList<ArticleResponseItem>
//)

//data class ArticleResponseItem(
//
//    @field:SerializedName("summary")
//    val summary: String,
//
//    @field:SerializedName("featured")
//    val featured: Boolean,
//
//    @field:SerializedName("publishedAt")
//    val publishedAt: String,
//
//    @field:SerializedName("imageUrl")
//    val imageUrl: String,
//
//    @field:SerializedName("newsSite")
//    val newsSite: String,
//
//    @field:SerializedName("id")
//    val id: Int,
//
//    @field:SerializedName("title")
//    val title: String,
//
//    @field:SerializedName("url")
//    val url: String,
//
//    @field:SerializedName("launches")
//    val launches: MutableList<Any>,
//
//    @field:SerializedName("events")
//    val events: MutableList<Any>,
//
//    @field:SerializedName("updatedAt")
//    val updatedAt: String
//)

data class SingleArticleResponse(

    @field:SerializedName("summary")
    val summary: String,

    @field:SerializedName("featured")
    val featured: Boolean,

    @field:SerializedName("publishedAt")
    val publishedAt: String,

    @field:SerializedName("imageUrl")
    val imageUrl: String,

    @field:SerializedName("newsSite")
    val newsSite: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("launches")
    val launches: List<Any>,

    @field:SerializedName("events")
    val events: List<Any>,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)
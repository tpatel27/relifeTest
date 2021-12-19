package com.tejas.relifemedicalsystemtest.network

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceApi {

    @GET("v3/articles")
    suspend fun getArticlesList(): Response<List<SingleArticleResponse>>

    /*
    * Not using this api since same data is obtained from the main api
    * and this only increases one unnecessary api call
    */
    @GET("v3/articles/{id}")
    suspend fun getSingleArticle(
        @Path("id") id: Int,
    ): Response<SingleArticleResponse>
}

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
    val launches: List<LaunchItem> = mutableListOf(),

    @field:SerializedName("events")
    val events: List<EventItem> = mutableListOf(),

    @field:SerializedName("updatedAt")
    val updatedAt: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        summary =parcel.readString() ?: "",
        featured = parcel.readByte() != 0.toByte(),
        publishedAt = parcel.readString() ?: "",
        imageUrl = parcel.readString() ?: "",
        newsSite = parcel.readString() ?: "",
        id = parcel.readInt(),
        title = parcel.readString() ?: "",
        url = parcel.readString() ?: "",
        launches = parcel.createTypedArrayList(LaunchItem) ?: mutableListOf(),
        events = parcel.createTypedArrayList(EventItem) ?: mutableListOf(),
        updatedAt = parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(summary)
        parcel.writeByte(if (featured) 1 else 0)
        parcel.writeString(publishedAt)
        parcel.writeString(imageUrl)
        parcel.writeString(newsSite)
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeTypedList(launches)
        parcel.writeTypedList(events)
        parcel.writeString(updatedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SingleArticleResponse> {
        override fun createFromParcel(parcel: Parcel): SingleArticleResponse {
            return SingleArticleResponse(parcel)
        }

        override fun newArray(size: Int): Array<SingleArticleResponse?> {
            return arrayOfNulls(size)
        }
    }
}

data class LaunchItem(

    @field:SerializedName("provider")
    val provider: String? = null,

    @field:SerializedName("id")
    val id: String? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(provider)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LaunchItem> {
        override fun createFromParcel(parcel: Parcel): LaunchItem {
            return LaunchItem(parcel)
        }

        override fun newArray(size: Int): Array<LaunchItem?> {
            return arrayOfNulls(size)
        }
    }
}

data class EventItem(

    @field:SerializedName("provider")
    val provider: String? = null,

    @field:SerializedName("id")
    val id: String? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(provider)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EventItem> {
        override fun createFromParcel(parcel: Parcel): EventItem {
            return EventItem(parcel)
        }

        override fun newArray(size: Int): Array<EventItem?> {
            return arrayOfNulls(size)
        }
    }
}
package com.tejas.relifemedicalsystemtest.data

import com.tejas.relifemedicalsystemtest.core.NetworkToUIProvider
import com.tejas.relifemedicalsystemtest.core.State
import com.tejas.relifemedicalsystemtest.network.ImageResponse
import com.tejas.relifemedicalsystemtest.network.PicApi
import com.tejas.relifemedicalsystemtest.network.SingleArticleResponse
import com.tejas.relifemedicalsystemtest.network.SpaceApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val api: SpaceApi,
    private val imageApi: PicApi
) {

    fun getArticlesList(): Flow<State<List<SingleArticleResponse>>> {
        return object : NetworkToUIProvider<List<SingleArticleResponse>>() {
            override suspend fun fetchFromRemote(): Response<List<SingleArticleResponse>> {
                return api.getArticlesList()
            }
        }.asFlow()
    }

    fun getImages(
        apiKey: String,
        query: String
    ): Flow<State<ImageResponse>> {
        return object : NetworkToUIProvider<ImageResponse>() {
            override suspend fun fetchFromRemote(): Response<ImageResponse> {
                return imageApi.getImages(
                    apiKey = apiKey,
                    query = query,
                )
            }
        }.asFlow()
    }
}
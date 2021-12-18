package com.tejas.relifemedicalsystemtest.ui

import com.tejas.relifemedicalsystemtest.core.NetworkToUIProvider
import com.tejas.relifemedicalsystemtest.core.State
import com.tejas.relifemedicalsystemtest.network.SingleArticleResponse
import com.tejas.relifemedicalsystemtest.network.SpaceApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: SpaceApi,
) {

    fun getArticlesList(): Flow<State<MutableList<SingleArticleResponse>>> {
        return object : NetworkToUIProvider<MutableList<SingleArticleResponse>>() {
            override suspend fun fetchFromRemote(): Response<MutableList<SingleArticleResponse>> {
                return api.getArticleList()
            }
        }.asFlow()
    }
}
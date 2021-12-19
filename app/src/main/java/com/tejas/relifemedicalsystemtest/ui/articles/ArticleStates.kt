package com.tejas.relifemedicalsystemtest.ui.articles

import com.tejas.relifemedicalsystemtest.network.SingleArticleResponse

sealed class ArticleStates

data class FailedArticleState(
    val errorMessage: String? = "",
) : ArticleStates()

data class SuccessfulArticleState(
    val isDataValid: Boolean = false
) : ArticleStates()

data class ArticlesResult(
    var isLoading: Boolean,
    val success: Boolean = false,
    val response: Any? = null,
    val message: String? = "",
    val responseList: List<SingleArticleResponse> = mutableListOf()
)
package com.tejas.relifemedicalsystemtest.ui.articles

import com.tejas.relifemedicalsystemtest.network.SingleArticleResponse

sealed class ArticleStates

data class FailedOnArticleState(
    val errorMessage: String? = "",
) : ArticleStates()

data class SuccessfulOnArticleState(
    val isDataValid: Boolean = false
) : ArticleStates()

data class ArticlesResult(
    var isLoading: Boolean,
    val success: Boolean = false,
    val response: Any? = null,
    val message: String? = "",
    val responseList: List<SingleArticleResponse> = mutableListOf()
)
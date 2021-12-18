package com.tejas.relifemedicalsystemtest.ui.articles

sealed class ArticleStates

data class FailedOnBoardState(
    val errorMessage: String? = "",
) : ArticleStates()

data class SuccessfulOnBoardState(
    val isDataValid: Boolean = false
) : ArticleStates()
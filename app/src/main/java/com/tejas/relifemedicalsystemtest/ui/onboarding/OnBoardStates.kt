package com.tejas.relifemedicalsystemtest.ui.onboarding

sealed class OnBoardStates

data class FailedOnBoardState(
    val errorMessage: String? = "",
) : OnBoardStates()

data class SuccessfulOnBoardState(
    val isDataValid: Boolean = false
) : OnBoardStates()

data class OnBoardResult(
    val success: Boolean = false,
    val response: Any? = null
)
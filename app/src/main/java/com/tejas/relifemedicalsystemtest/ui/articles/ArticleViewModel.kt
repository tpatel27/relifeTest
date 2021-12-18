package com.tejas.relifemedicalsystemtest.ui.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tejas.relifemedicalsystemtest.core.State
import com.tejas.relifemedicalsystemtest.data.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _articleResult = MutableLiveData<ArticlesResult>()
    val articleResult: LiveData<ArticlesResult> = _articleResult

    fun getArticlesList() {
        viewModelScope.launch {
            repository.getArticlesList().catch {
                Timber.d("Error fetching Articles")
            }.collect { state ->
                when (state) {
                    is State.Loading -> _articleResult.value = ArticlesResult(
                        isLoading = true,
                    )
                    is State.Success -> {
                        _articleResult.value = ArticlesResult(
                            isLoading = false,
                            success = true,
                            responseList = state.data,
                        )
                    }
                    is State.ResponseError -> {
                        _articleResult.value = ArticlesResult(
                            isLoading = false,
                            success = false,
                            message = state.message,
                        )
                    }
                    is State.ExceptionError -> {
                        _articleResult.value = ArticlesResult(
                            isLoading = false,
                            success = false,
                            message = state.errorMessage,
                        )
                    }
                }
            }
        }
    }
}
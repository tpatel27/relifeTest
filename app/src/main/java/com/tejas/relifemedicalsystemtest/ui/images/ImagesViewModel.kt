package com.tejas.relifemedicalsystemtest.ui.images

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
class ImagesViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _imageResult = MutableLiveData<ImageResult>()
    val imageResult: LiveData<ImageResult> = _imageResult

    fun getImages(
        apiKey: String,
        query: String
    ) {
        viewModelScope.launch {
            repository.getImages(
                apiKey = apiKey,
                query = query
            ).catch {
                Timber.d("Error fetching Images")
            }.collect { state ->
                when (state) {
                    is State.Loading -> _imageResult.value = ImageResult(
                        isLoading = true,
                    )
                    is State.Success -> {
                        _imageResult.value = ImageResult(
                            isLoading = false,
                            success = true,
                            response = state.data,
                        )
                    }
                    is State.ResponseError -> {
                        _imageResult.value = ImageResult(
                            isLoading = false,
                            success = false,
                            message = state.message,
                        )
                    }
                    is State.ExceptionError -> {
                        _imageResult.value = ImageResult(
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
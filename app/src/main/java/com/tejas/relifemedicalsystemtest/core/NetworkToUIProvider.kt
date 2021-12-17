package com.tejas.relifemedicalsystemtest.core

import androidx.annotation.MainThread
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import timber.log.Timber
import java.io.EOFException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

abstract class NetworkToUIProvider<RESULT> {

    fun asFlow() = flow<State<RESULT>> {

        // Emit Loading State
        emit(State.Loading())

        // Fetch latest posts from remote
        val apiResponse = fetchFromRemote()
        val errorBody = apiResponse.errorBody()

        // Parse body
        val responseBody = apiResponse.body()

        // Check for response validation
        if (apiResponse.isSuccessful && responseBody != null) {
            // Save posts into the persistence storage
            emit(State.Success(responseBody))
        } else {
            // Something went wrong! Emit Error state.
            when (apiResponse.code()) {
                HttpURLConnection.HTTP_UNAUTHORIZED -> emit(
                    State.ResponseError(
                        code = apiResponse.code(),
                        message = "Unauthorised access.",
                        errorBody = errorBody
                    )
                )
                HttpURLConnection.HTTP_BAD_GATEWAY -> emit(
                    State.ResponseError(
                        code = apiResponse.code(),
                        message = "Server Down, please try again later",
                        errorBody = errorBody
                    )
                )
                else -> emit(
                    State.ResponseError(
                        code = apiResponse.code(),
                        message = "Error retrieving information from server",
                        errorBody = errorBody
                    )
                )
            }
        }

    }.catch { e ->
        // Exception occurred! Emit error
        Timber.e("Error -> $e")
        when (e) {
            is IllegalStateException -> emit(
                State.ExceptionError(
                    errorMessage = "Oops!, Error with Data Parsing",
                    throwable = e
                )
            )
            is SocketTimeoutException -> emit(
                State.ExceptionError(
                    errorMessage = "Network error! Can't get latest data. Server timeout occurred with that request",
                    throwable = e
                )
            )
            is EOFException -> emit(
                State.ExceptionError("EOFException has occurred", throwable = e)
            )
            else -> emit(
                State.ExceptionError(
                    "Network error! Can't get latest data.",
                    throwable = e
                )
            )
        }
        e.printStackTrace()
    }

    /**
     * Fetches [Response] from the remote end point.
     */
    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<RESULT>
}
package com.tangerine.core.api.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry
import retrofit2.Call


abstract class BaseRepo(private val dispatcher: CoroutineDispatcher) {
    private var currentDelay = 3000L
    private var retries = 3L

    sealed interface ApiResponse {
        class Success<T>(val response: T) : ApiResponse

        data class Failure(val throwable: Throwable) : ApiResponse
    }

    fun <T> request(
        call: Call<T>
    ): Flow<ApiResponse> = flow {
        val response = call.clone().execute() //Make sure new call will be created

        when {
            !response.isSuccessful -> throw Throwable("${response.code()}: ${response.errorBody()}")
            response.body() == null -> throw Throwable("Empty response !")
            else -> emit(ApiResponse.Success(response.body()) as ApiResponse)
        }
    }.flowOn(dispatcher).retry(retries = retries) {
        delay(currentDelay)
        return@retry true
    }.catch {
        emit(ApiResponse.Failure(it) as ApiResponse)
    }
}
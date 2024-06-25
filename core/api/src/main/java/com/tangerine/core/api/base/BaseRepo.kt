package com.tangerine.core.api.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call

abstract class BaseRepo {
    sealed interface ApiResponse {
        class Success<T>(val response: T) : ApiResponse

        data class Failure(val throwable: Throwable) : ApiResponse
    }

    open fun <T> request(
        call: Call<T>
    ): Flow<ApiResponse> = flow {
        val response = call.execute()

        emit(
            when {
                response.isSuccessful && response.body() != null -> ApiResponse.Success(response.body())
                else -> ApiResponse.Failure(Throwable("${response.code()}:${response.errorBody()}"))
            }
        )
    }.flowOn(Dispatchers.IO).catch {
        emit(ApiResponse.Failure(it))
    }
}
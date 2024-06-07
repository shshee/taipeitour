package com.tangerine.core.api.base

import com.tangerine.core.model.BaseUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call

abstract class BaseRepo {
    sealed interface ApiReponse {
        class Success<T>(val response: T) : ApiReponse

        data class Failure(val throwable: Throwable) : ApiReponse
    }

    open fun <T> request(
        call: Call<T>
    ): Flow<ApiReponse> = flow {
        val response = call.execute()

        emit(
            when {
                response.isSuccessful && response.body() != null -> ApiReponse.Success(response.body())
                else -> ApiReponse.Failure(Throwable("${response.code()}:${response.errorBody()}"))
            }
        )
    }.flowOn(Dispatchers.IO).catch {
        emit(ApiReponse.Failure(it))
    }
}
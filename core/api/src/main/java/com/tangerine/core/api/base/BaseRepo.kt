package com.tangerine.core.api.base

import android.content.Context
import com.tangerine.core.ultis.isNetworkAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.Call

abstract class BaseRepo(private val context: Context) {
    interface ApiResponse<T> {
        fun onSuccess(result: T)
        fun onFailure(message: String?)
    }

    open fun <T> request(
        call: Call<T>,
        resp: ApiResponse<T>
    ) {
        fun returnError(message: String?) = resp.onFailure(message)

        try {
            if (context.isNetworkAvailable()) {
                val doCall = flow {
                    emit(call.execute())
                }.flowOn(Dispatchers.IO)

                CoroutineScope(Dispatchers.Main).launch {
                    doCall.collect { response ->
                        when {
                            response.isSuccessful && response.body() != null -> resp.onSuccess(
                                response.body()!!
                            )

                            else -> returnError("${response.code()}:${response.errorBody()}")
                        }
                    }
                }
            } else returnError(context.getString(com.tangerine.core.source.R.string.error_no_internet_connection))
        } catch (exception: Throwable) {
            returnError(exception.message)
        }
    }
}
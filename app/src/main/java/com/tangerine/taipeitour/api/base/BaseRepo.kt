package com.tangerine.taipeitour.api.base

import android.content.Context
import android.os.Message
import com.tangerine.taipeitour.R
import com.tangerine.taipeitour.TaipeiTourApplication
import com.tangerine.taipeitour.utils.isNetworkAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            } else returnError(context.getString(R.string.error_no_internet_connection))
        } catch (exception: Throwable) {
            returnError(exception.message)
        }
    }
}
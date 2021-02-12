package com.happinessinc.getshwifty.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.happinessinc.getshwifty.repo.api.ApiResponse
import kotlinx.coroutines.Dispatchers

fun <T, A> performGetOperation(databaseQuery: () -> LiveData<T>,
                               networkCall: suspend () -> ApiResponse<A>,
                               saveCallResult: suspend (A) -> Unit): LiveData<ApiResponse<T>> =
    liveData(Dispatchers.IO) {
        emit(ApiResponse.loading())
        val source = databaseQuery.invoke().map { ApiResponse.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == ApiResponse.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)

        } else if (responseStatus.status == ApiResponse.Status.ERROR) {
            emit(ApiResponse.error(responseStatus.message!!))
            emitSource(source)
        }
    }

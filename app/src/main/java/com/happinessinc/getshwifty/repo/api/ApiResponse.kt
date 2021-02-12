package com.happinessinc.getshwifty.repo.api

data class ApiResponse<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): ApiResponse<T> {
            return ApiResponse(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): ApiResponse<T> {
            return ApiResponse(Status.LOADING, data, null)
        }
    }
}
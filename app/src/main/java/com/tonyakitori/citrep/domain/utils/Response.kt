package com.tonyakitori.citrep.domain.utils

sealed class Response<out T> {

    object Loading : Response<Nothing>()

    data class Success<out T>(val data: T) : Response<T>()
    data class Error<out T>(val error: Throwable) : Response<T>()

}
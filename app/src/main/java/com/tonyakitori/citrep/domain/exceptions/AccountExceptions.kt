package com.tonyakitori.citrep.domain.exceptions

sealed class AccountExceptions(message: String?) : Exception(message) {

    class TooManyRequests(message: String?) : AccountExceptions(message)

}
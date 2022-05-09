package com.tonyakitori.citrep.domain.exceptions

sealed class LoginExceptions(message: String?) : Exception(message) {

    class InvalidCredentials(message: String?) : LoginExceptions(message)

}
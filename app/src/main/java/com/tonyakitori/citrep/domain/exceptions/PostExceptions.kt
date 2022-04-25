package com.tonyakitori.citrep.domain.exceptions

sealed class PostExceptions(message: String? = null) : Exception(message) {

    class PostEmpty(message: String = "The post is empty") : PostExceptions(message)
    class UserNotFound(message: String = "User not found to create post") : PostExceptions(message)

}
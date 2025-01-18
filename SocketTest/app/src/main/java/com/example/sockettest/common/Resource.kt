package com.example.sockettest.common

sealed class Resource<out T> {

    data class Success<out T>(val data: T) : Resource<T>()

    data class Failure<out T>(
        val errorCode: Int? = null,
        val errorMessage: String? = null,
        val exception: Throwable? = null
    ) : Resource<T>()

    data class Warning<out T>(
        val errorCode: Int? = null,
        val errorMessage: String? = null,
        val exception: Throwable? = null
    ) : Resource<T>()
}
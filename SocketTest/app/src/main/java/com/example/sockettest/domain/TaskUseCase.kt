package com.example.sockettest.domain

import com.example.sockettest.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class TaskUseCase<Params, T>() {
    suspend fun invoke(params: Params): Resource<T> =
        withContext(Dispatchers.IO) {
            runCatching {
                Resource.Success(buildTask(params))
            }.getOrElse { exception ->
                Resource.Failure(exception = exception)
            }
        }

    abstract suspend fun buildTask(params: Params): T
}
package com.example.sockettest.domain

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<Params, T> {

    abstract fun invoke(params: Params): Flow<T>
}
package com.example.sockettest.domain.chat

import com.example.sockettest.data.remote.repository.local.MessageRepository
import com.example.sockettest.domain.TaskUseCase
import com.example.sockettest.model.response.ChatResponse
import javax.inject.Inject

class ClearHistoryUseCase @Inject constructor(
    private val repository: MessageRepository
) : TaskUseCase<Unit, Unit>() {

    override suspend fun buildTask(params: Unit) {
        repository.clearHistory()
    }
}
package com.example.sockettest.domain.chat

import com.example.sockettest.data.remote.repository.local.MessageRepository
import com.example.sockettest.domain.TaskUseCase
import com.example.sockettest.model.response.ChatResponse
import javax.inject.Inject

class InsertMessageUseCase @Inject constructor(
    private val repository: MessageRepository
) : TaskUseCase<ChatResponse?, Unit>() {

    override suspend fun buildTask(params: ChatResponse?) {
        params?.let {
            repository.insertMessage(it)
        }
    }
}
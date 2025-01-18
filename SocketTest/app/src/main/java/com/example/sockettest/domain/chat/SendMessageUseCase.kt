package com.example.sockettest.domain.chat

import com.example.sockettest.data.remote.repository.ChatRepository
import com.example.sockettest.domain.TaskUseCase
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val coinbaseRepository: ChatRepository) :
    TaskUseCase<String, Unit>() {

    override suspend fun buildTask(params: String) {
        coinbaseRepository.sendMessage(params)
    }
}
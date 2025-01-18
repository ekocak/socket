package com.example.sockettest.domain.chat

import com.example.sockettest.data.remote.repository.ChatRepository
import com.example.sockettest.domain.TaskUseCase
import javax.inject.Inject

class DisconnectUseCase @Inject constructor(private val coinbaseRepository: ChatRepository) :
    TaskUseCase<Unit, Unit>() {
    override suspend fun buildTask(params: Unit) {
        coinbaseRepository.disconnect()
    }
}
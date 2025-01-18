package com.example.sockettest.domain.chat

import com.example.sockettest.data.remote.repository.ChatRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(private val coinbaseRepository: ChatRepository) {
    operator fun invoke() =
        coinbaseRepository.receiveMessages()
}
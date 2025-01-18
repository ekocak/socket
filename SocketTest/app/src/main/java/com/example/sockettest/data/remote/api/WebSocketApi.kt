package com.example.sockettest.data.remote.api

import Receive
import Send
import com.example.sockettest.domain.model.SocketState
import kotlinx.coroutines.flow.Flow

interface WebSocketApi {
    @Send
    fun sendMessage(message: String)

    @Receive
    fun receiveMessage(): Flow<SocketState>
}
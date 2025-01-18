package com.example.sockettest.data.remote.repository

import com.example.sockettest.data.remote.api.WebSocketApi
import com.example.sockettest.data.remote.client.WebSocketClient
import com.example.sockettest.domain.model.SocketState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val webSocketApi: WebSocketApi,
    private val webSocketClient: WebSocketClient
) : ChatRepository {
    override suspend fun sendMessage(message: String) {
        webSocketApi.sendMessage(message)
    }

    override suspend fun connect() {
     webSocketClient.connect()
    }

    override suspend fun disconnect() {
        webSocketClient.close()
    }

    override fun receiveMessages(): Flow<SocketState> {
        return webSocketApi.receiveMessage()
    }
}
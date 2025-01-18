package com.example.sockettest.data.remote.repository

import com.example.sockettest.domain.model.SocketState
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun sendMessage(message: String)
    suspend fun connect()
    suspend fun disconnect()
    fun receiveMessages(): Flow<SocketState>
}
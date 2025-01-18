package com.example.sockettest.domain.model

sealed interface SocketState {
    object Connected : SocketState
    object Disconnected : SocketState
    object Connecting : SocketState
    data class MessageReceived(val message: String) : SocketState
    data class Failed(val reason: String?) : SocketState
}
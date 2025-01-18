package com.example.sockettest.data.remote.client

import com.example.sockettest.domain.retry.BackoffStrategy
import com.example.sockettest.domain.model.SocketState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Singleton
class WebSocketClient(
    private val url: String,
    private val client: OkHttpClient,
    private val backoffStrategy: BackoffStrategy
) {
    private var webSocket: WebSocket? = null
    private val receiveChannel = Channel<SocketState>()

    suspend fun connect() {
        startConnect()
    }

    private fun establishConnection() {
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                receiveChannel.trySend(SocketState.Connected)
            }
            override fun onMessage(webSocket: WebSocket, text: String) {
                receiveChannel.trySend(SocketState.MessageReceived(text))
            }
            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                receiveChannel.trySend(SocketState.Failed(response?.message))
                CoroutineScope(Dispatchers.IO).launch {
                    startConnect()
                }

                throw t
            }
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                receiveChannel.trySend(SocketState.Disconnected)
            }
        })
    }
    fun send(message: String) {
        webSocket?.send(message)
    }

    fun receive(): Flow<SocketState> = receiveChannel.receiveAsFlow()

    fun close() {
        webSocket?.close(1000, "Client closed")
        client.dispatcher.executorService.shutdown()
    }
    private suspend fun startConnect() {
        var attempt = 0
        while (true) {
            try {
                establishConnection()
                break
            } catch (e: Exception) {
                attempt++
                val retryDelay = backoffStrategy.getDelay(attempt)
                if (retryDelay == null) {
                    receiveChannel.trySend(SocketState.Failed("---"))
                    break
                }
                delay(retryDelay)
            }
        }
    }
}
package com.example.sockettest

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.sockettest.AdvancedWebSocketManager.ConnectionState.*
import okhttp3.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdvancedWebSocketManager @Inject constructor(private val context: Context) {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null
    private var isConnected = false
    private var reconnectAttempts = 0
    private val maxReconnectAttempts = 15
    private val executor = Executors.newSingleThreadScheduledExecutor()
    private val serverUrl = "wss://echo.websocket.org" // Örnek URL
    enum class ConnectionState { CONNECTING, CONNECTED, DISCONNECTED, RECONNECTING }
    var connectionState: ConnectionState = DISCONNECTED
    fun connect() {
        if (connectionState == CONNECTED || connectionState == CONNECTING) return
        if (!isInternetAvailable()) {
            println("İnternet bağlantısı yok. Yeniden bağlanma erteleniyor.")
            scheduleReconnect()
            return
        }
        connectionState = CONNECTING
        val request = Request.Builder().url(serverUrl).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                println("WebSocket bağlantısı kuruldu.")
                connectionState = CONNECTED
                isConnected = true
                reconnectAttempts = 0
            }
            override fun onMessage(webSocket: WebSocket, text: String) {
                println("Mesaj alındı: $text")
                handleIncomingMessage(text)
            }
            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                println("Bağlantı kapanıyor: $code / $reason")
                connectionState = DISCONNECTED
                webSocket.close(1000, null)
            }
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                println("Bağlantı kapandı: $code / $reason")
                connectionState = DISCONNECTED
                isConnected = false
                scheduleReconnect()
            }
            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                println("Hata oluştu: ${t.message}")
                connectionState = DISCONNECTED
                isConnected = false
                scheduleReconnect()
            }
        })
    }
    private fun scheduleReconnect() {
        if (reconnectAttempts >= maxReconnectAttempts) {
            println("Maksimum yeniden bağlanma denemesi aşıldı.")
            return
        }
        reconnectAttempts++
        val delay = (Math.pow(2.0, reconnectAttempts.toDouble()) * 1000L).toLong()
        println("Yeniden bağlanma ${reconnectAttempts}. kez ${delay}ms sonra deneniyor.")
        executor.schedule({
            connect()
        }, delay, TimeUnit.MILLISECONDS)
    }
    private fun isInternetAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
    fun disconnect() {
        webSocket?.close(1000, "Kullanıcı tarafından kapatıldı.")
        connectionState = DISCONNECTED
        isConnected = false
    }
    fun sendMessage(message: String) {
        if (isConnected) {
            webSocket?.send(message)
        } else {
            println("Mesaj gönderilemedi, WebSocket bağlantısı yok.")
        }
    }
    private fun handleIncomingMessage(message: String) {
        // Gelen mesajları işlemek için gerekli işlemleri buraya yazın.
        println("Gelen Mesaj: $message")
    }
}
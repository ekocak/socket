package com.example.sockettest.data.remote.client

import Receive
import Send
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
class WebSocketApiProxy(
    private val webSocketClient: WebSocketClient
) : InvocationHandler {
    override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any? {
        return when {
            method.isAnnotationPresent(Send::class.java) -> {
                args?.get(0)?.let { webSocketClient.send(it.toString()) }
            }
            method.isAnnotationPresent(Receive::class.java) -> {
                println("receive calisti")
                webSocketClient.receive()
            }
            else -> throw UnsupportedOperationException("Unsupported method: ${method.name}")
        }
    }
}
inline fun <reified T> createWebSocketApi(webSocketClient: WebSocketClient): T {
    return Proxy.newProxyInstance(
        T::class.java.classLoader,
        arrayOf(T::class.java),
        WebSocketApiProxy(webSocketClient)
    ) as T
}
package com.example.sockettest.di

import android.content.Context
import com.example.sockettest.AdvancedWebSocketManager
import com.example.sockettest.App
import com.example.sockettest.data.remote.api.WebSocketApi
import com.example.sockettest.data.remote.client.WebSocketClient
import com.example.sockettest.data.remote.client.createWebSocketApi
import com.example.sockettest.data.remote.repository.ChatRepository
import com.example.sockettest.data.remote.repository.ChatRepositoryImpl
import com.example.sockettest.domain.retry.BackoffStrategy
import com.example.sockettest.domain.retry.ExponentialBackoffStrategy

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {


    @Provides
    @Singleton
    fun providesApplication(@ApplicationContext context: Context): App {
        return context as App
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }


    @Singleton
    @Provides
    fun provideWebSocketClient(
        okHttpClient: OkHttpClient,
        backoffStrategy: BackoffStrategy
    ): WebSocketClient {
        return WebSocketClient(
            url = BASE_URL,
            client = okHttpClient,
            backoffStrategy = backoffStrategy
        )
    }
    @Singleton
    @Provides
    fun provideBackoffStrategy(): BackoffStrategy {
        return ExponentialBackoffStrategy()
    }

    @Provides
    @Singleton
    fun provideWebSocketApi(webSocketClient: WebSocketClient): WebSocketApi {
        return createWebSocketApi(webSocketClient)
    }

    @Provides
    @Singleton
    fun provideChatRepository(
        webSocketApi: WebSocketApi,
        webSocketClient: WebSocketClient
    ): ChatRepository {
        return ChatRepositoryImpl(webSocketApi, webSocketClient)
    }

    companion object {
        const val BASE_URL = "wss://echo.websocket.org"
    }
}
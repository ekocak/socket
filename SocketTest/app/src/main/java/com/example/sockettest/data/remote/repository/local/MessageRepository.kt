package com.example.sockettest.data.remote.repository.local
import com.example.sockettest.db.AppDatabase
import com.example.sockettest.model.response.ChatResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
    private val database: AppDatabase
) {
    suspend fun insertMessage(chatResponse: ChatResponse) =
        database.chatDao().insert(chatResponse)

    suspend fun getAll() = database.chatDao().getAll()

    suspend fun clearHistory() = database.chatDao().clearHistory()

}
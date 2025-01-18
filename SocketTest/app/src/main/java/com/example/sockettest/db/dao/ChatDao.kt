package com.example.sockettest.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.ajet.mobile.db.dao.BaseDao
import com.example.sockettest.model.response.ChatResponse

@Dao
interface ChatDao : BaseDao<ChatResponse> {

    @Query("SELECT * FROM chatresponse")
    suspend fun getAll(): List<ChatResponse?>?

    @Query("SELECT COUNT(*) FROM chatresponse")
    suspend fun getCount(): Int

    @Query("DELETE FROM chatresponse")
    suspend fun clearHistory(): Int

}
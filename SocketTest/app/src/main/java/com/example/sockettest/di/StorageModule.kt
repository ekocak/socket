package com.example.sockettest.di

import android.content.Context
import androidx.room.Room
import com.example.sockettest.db.AppDatabase
import com.example.sockettest.db.dao.ChatDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    private const val APP_DB_NAME = "cb-db"

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return  Room.databaseBuilder(
            context, AppDatabase::class.java, APP_DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideVehicleDao(database: AppDatabase): ChatDao =
        database.chatDao()

}
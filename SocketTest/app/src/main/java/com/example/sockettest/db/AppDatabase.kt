package com.example.sockettest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.sockettest.db.dao.ChatDao
import com.example.sockettest.extensions.fromJson
import com.example.sockettest.model.response.ChatResponse
import com.google.gson.Gson

@Database(
    entities = [ChatResponse::class],
    version = AppDatabase.DB_VERSION,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        /**
         * Database version containing only the ChatResponse table
         */
        private const val VERSION_ONE = 1

        /**
         * if any tablo added write here now only Chatresponse containing
         */
        private const val VERSION_TWO = 2

        const val DB_VERSION = VERSION_ONE

        val MIGRATION_1_2 = object : Migration(VERSION_ONE, VERSION_TWO) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
               
            )""".trimIndent()
                )
            }
        }

    }

    abstract fun chatDao(): ChatDao

}
class Converters {
    @TypeConverter
    fun fromAnyToJson(value: Any?): String? {
        println("sss kaydedildi : "+Gson().toJson(value))
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromJsonToAny(value: String?): Any? {
        if (value == null) return null
        println("sss okundu : "+Gson().fromJson(value, Any::class.java))
        return Gson().fromJson(value, Any::class.java)
    }
}

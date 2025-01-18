package com.example.sockettest.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


inline fun <reified T> String.fromJson(): T {
    val type = object : TypeToken<T>() {}.type
    return runCatching {
        Gson().fromJson<T>(this, type)
    }.getOrNull() ?: throw IllegalStateException("Failed to parse JSON response")
}

fun String?.safeString(): String {
    return this ?: ""
}




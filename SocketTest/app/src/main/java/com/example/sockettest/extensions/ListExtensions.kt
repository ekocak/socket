package com.example.sockettest.extensions

import com.example.sockettest.model.response.ChatResponse
import com.example.sockettest.model.response.ContentType
import com.google.gson.Gson

fun List<ChatResponse>.mapContent(): List<ChatResponse> {
    return this.map { step ->
        val mappedContent = when (step.type) {
            "button" -> {
                val content = Gson().toJson(step.content).fromJson<ContentType.ButtonContent>()
                content
            }
            "text" -> {
                if (step.content is String) {
                    val text = step.content as? String ?: throw IllegalStateException("Invalid text content")
                    ContentType.TextContent(text)
                }else {
                    val content = Gson().toJson(step.content).fromJson<ContentType.TextContent>()
                    content
                }

            }
            "image" -> {
                if (step.content is String) {
                    val url = step.content as? String ?: throw IllegalStateException("Invalid image content")
                    ContentType.ImageContent(url)
                }else {
                    val content = Gson().toJson(step.content).fromJson<ContentType.ImageContent>()
                    content
                }
            }
            else -> throw IllegalStateException("Unknown type: ${step.type}")
        }
        step.copy(content = mappedContent)
    }
}
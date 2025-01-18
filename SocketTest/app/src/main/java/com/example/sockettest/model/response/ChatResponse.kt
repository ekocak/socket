package com.example.sockettest.model.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatResponse(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val step: String,
    val type: String,
    val content: Any,
    val action: String
)

sealed class ContentType {
    data class ButtonContent(
        val text: String,
        val buttons: List<Button>
    ) : ContentType()
    data class TextContent(
        val text: String
    ) : ContentType()
    data class ImageContent(
        val url: String
    ) : ContentType()
}
data class Button(
    val label: String,
    val action: String
)


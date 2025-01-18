package com.example.sockettest.ui.costumerservice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.sockettest.R
import com.example.sockettest.databinding.ItemButtonBinding
import com.example.sockettest.databinding.ItemImageBinding
import com.example.sockettest.databinding.ItemTextBinding
import com.example.sockettest.model.response.ChatResponse
import com.example.sockettest.model.response.ContentType

class ChatAdapter(
    private val onButtonClick: ((String) -> Unit)? = null
) : ListAdapter<ChatResponse, StepViewHolder>(DIFF_CALLBACK) {
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).content) {
            is ContentType.TextContent -> R.layout.item_text
            is ContentType.ButtonContent -> R.layout.item_button
            is ContentType.ImageContent -> R.layout.item_image
            else -> throw IllegalStateException("Unknown content type")
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_text -> {
                val binding = ItemTextBinding.inflate(inflater, parent, false)
                StepViewHolder.TextViewHolder(binding)
            }
            R.layout.item_button -> {
                val binding = ItemButtonBinding.inflate(inflater, parent, false)
                StepViewHolder.ButtonViewHolder(binding)
            }
            R.layout.item_image -> {
                val binding = ItemImageBinding.inflate(inflater, parent, false)
                StepViewHolder.ImageViewHolder(binding)
            }
            else -> throw IllegalStateException("Unknown view type")
        }
    }
    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val step = getItem(position)
        when (holder) {
            is StepViewHolder.TextViewHolder -> holder.bind(step.content as ContentType.TextContent)
            is StepViewHolder.ButtonViewHolder -> holder.bind(step.content as ContentType.ButtonContent, onButtonClick)
            is StepViewHolder.ImageViewHolder -> holder.bind(step.content as ContentType.ImageContent)
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ChatResponse>() {
            override fun areItemsTheSame(oldItem: ChatResponse, newItem: ChatResponse): Boolean {
                return oldItem.step == newItem.step
            }
            override fun areContentsTheSame(oldItem: ChatResponse, newItem: ChatResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}
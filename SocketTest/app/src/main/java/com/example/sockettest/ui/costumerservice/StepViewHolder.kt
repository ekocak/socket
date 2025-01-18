package com.example.sockettest.ui.costumerservice


import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.sockettest.databinding.ItemButtonBinding
import com.example.sockettest.databinding.ItemImageBinding
import com.example.sockettest.databinding.ItemTextBinding
import com.example.sockettest.model.response.ContentType
import com.google.android.material.button.MaterialButton

sealed class StepViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    class TextViewHolder(private val binding: ItemTextBinding) : StepViewHolder(binding) {
        fun bind(content: ContentType.TextContent) {
            binding.textView.text = content.text
        }
    }
    class ButtonViewHolder(private val binding: ItemButtonBinding) : StepViewHolder(binding) {
        fun bind(content: ContentType.ButtonContent, onButtonClick: ((String) -> Unit)?) {
            binding.textView.text = content.text
            binding.buttonContainer.removeAllViews()
            content.buttons.forEach { button ->
                val buttonView = MaterialButton(binding.root.context).apply {
                    text = button.label
                    setOnClickListener { onButtonClick?.invoke(button.action) }
                }
                binding.buttonContainer.addView(buttonView)
            }
        }
    }
    class ImageViewHolder(private val binding: ItemImageBinding) : StepViewHolder(binding) {
        fun bind(content: ContentType.ImageContent) {
            Glide.with(binding.imageView.context)
                .load(content.url)
                .into(binding.imageView)
        }
    }
}
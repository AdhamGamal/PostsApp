package com.amg.postsapp.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.amg.postsapp.data.model.Post

class DiffCallback : DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem == newItem
}

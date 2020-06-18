package com.amg.postsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amg.postsapp.databinding.PostBinding
import com.amg.postsapp.data.model.Post
import com.amg.postsapp.ui.ListFragmentDirections
import javax.inject.Inject

class PostsAdapter @Inject constructor(): PagingDataAdapter<Post, PostsAdapter.PostViewHolder>(DiffCallback()) {

    class PostViewHolder(private var binding: PostBinding) :
            RecyclerView.ViewHolder(binding.root) {

        private var post: Post? = null

        init {
            binding.root.setOnClickListener {
                it.findNavController().navigate(ListFragmentDirections.toDetailsFragment(post!!.id))
            }
        }

        fun bind(post: Post) {
            this.post = post
            binding.post = post
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
                PostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            holder.bind(repoItem)
        }
    }
}

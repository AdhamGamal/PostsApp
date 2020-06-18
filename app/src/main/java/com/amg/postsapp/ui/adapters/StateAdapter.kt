package com.amg.postsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amg.postsapp.databinding.FooterBinding
import com.amg.postsapp.ui.toVisibility
import javax.inject.Inject

class StateAdapter @Inject constructor() : LoadStateAdapter<StateAdapter.ReposLoadStateViewHolder>() {

    class ReposLoadStateViewHolder(private val binding: FooterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.progressBar.visibility = toVisibility(loadState is LoadState.Loading)
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ReposLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ReposLoadStateViewHolder {
        return ReposLoadStateViewHolder(FooterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}

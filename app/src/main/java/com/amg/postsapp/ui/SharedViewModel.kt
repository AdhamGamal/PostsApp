package com.amg.postsapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.amg.postsapp.data.Repository
import com.amg.postsapp.data.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SharedViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {

    var isLoading = false

    @ExperimentalPagingApi
    fun getPosts(): Flow<PagingData<Post>> {
        return repository.getPosts()
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun getPost(id: Int) = repository.getPost(id)

}
package com.amg.postsapp.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.amg.postsapp.data.Repository
import com.amg.postsapp.data.model.Post

// Can't have a Construct Injection
// because it depend on repository and repository depend on it so conflict happen
@ExperimentalPagingApi
class PagingMediator(private val repository: Repository) : RemoteMediator<Int, Post>() {

    val STARTING_PAGE_INDEX = 1

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Post>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    getClosestKeyToCurrentPosition(state) ?: STARTING_PAGE_INDEX
                }
                LoadType.APPEND -> {
                    getLastItemKey(state)!! + 1
                }
                LoadType.PREPEND -> {
                    0
                }
            }

            val posts = repository.requestPosts(page)

            val endOfPaginationReached = posts.isEmpty()

            if (!endOfPaginationReached) {
                repository.insertAll(posts)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            // Doesn't load  data from room when there is no network (issue)
            // MediatorResult.Error(exception)

            Log.e(">>>>>>>>>>", exception.toString())
            MediatorResult.Success(endOfPaginationReached = true)
        }
    }

    private fun getLastItemKey(state: PagingState<Int, Post>): Int? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.userId
    }

    private fun getClosestKeyToCurrentPosition(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { position -> state.closestItemToPosition(position)?.userId }
    }

}
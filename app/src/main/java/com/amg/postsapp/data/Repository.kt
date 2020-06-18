package com.amg.postsapp.data

import androidx.paging.*
import com.amg.postsapp.data.api.RetrofitService
import com.amg.postsapp.data.model.Post
import com.amg.postsapp.data.paging.PagingMediator
import com.amg.postsapp.data.room.PostsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository
@Inject constructor(private val dao: PostsDao, private val service: RetrofitService) {

    val NETWORK_PAGE_SIZE = 10
    private val pagingSourceFactory = { dao.getAll() }

    @ExperimentalPagingApi
    fun getPosts(): Flow<PagingData<Post>> {
        return Pager(
                config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
                remoteMediator = PagingMediator(this),
                pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun requestPosts(id: Int) = service.getPosts(id)


    fun getPost(id: Int) = dao.getPost(id)

    suspend fun insertAll(posts: List<Post>) = dao.insert(posts)


    suspend fun deleteAll() = dao.deleteAll()

}

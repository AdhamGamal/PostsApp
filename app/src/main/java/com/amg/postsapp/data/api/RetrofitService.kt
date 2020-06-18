package com.amg.postsapp.data.api

import com.amg.postsapp.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("posts")
    suspend fun getPosts(
            @Query("userId") userId: Int
    ): List<Post>
}
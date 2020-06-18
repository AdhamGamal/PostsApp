package com.amg.postsapp.data.room

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amg.postsapp.data.model.Post

@Dao
interface PostsDao {

    @Query("SELECT * FROM posts")
    fun getAll(): PagingSource<Int, Post>

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getPost(id: Int): LiveData<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(posts: List<Post>)

    @Query("DELETE FROM posts")
    suspend fun deleteAll()
}

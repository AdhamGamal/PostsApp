package com.amg.postsapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amg.postsapp.data.model.Post

@Database(entities = [Post::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao
}

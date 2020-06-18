package com.amg.postsapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class Post(
        val userId: Int,
        @PrimaryKey
        val id: Int,
        val title: String,
        val body: String
)

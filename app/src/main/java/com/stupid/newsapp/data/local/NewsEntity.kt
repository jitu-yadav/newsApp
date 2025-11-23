package com.stupid.newsapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "bookmarks_articles")
data class NewsEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)

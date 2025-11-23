package com.stupid.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [ArticleEntity::class],
    version = 2,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract val newsDao: ArticleDao
}



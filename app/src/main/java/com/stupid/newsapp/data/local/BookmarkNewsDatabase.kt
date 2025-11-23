package com.stupid.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [NewsEntity::class],
    version = 2,
    exportSchema = false
)
abstract class BookmarkNewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsArticleDao
}



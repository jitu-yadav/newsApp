package com.stupid.newsapp.di

import android.content.Context
import androidx.room.Room
import com.stupid.newsapp.common.Utils
import com.stupid.newsapp.data.local.BookmarkNewsDatabase
import com.stupid.newsapp.data.local.NewsArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookmarkNewsDbModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BookmarkNewsDatabase {
        return Room.databaseBuilder(
            context,
            BookmarkNewsDatabase::class.java,
            Utils.DATABASE_NAME
        )
        .build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(database: BookmarkNewsDatabase): NewsArticleDao = database.newsDao()
}
package com.stupid.newsapp.di

import android.content.Context
import androidx.room.Room
import com.stupid.newsapp.common.Utils
import com.stupid.newsapp.data.local.NewsDatabase
import com.stupid.newsapp.data.local.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            Utils.DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(database: NewsDatabase): ArticleDao = database.newsDao
}
package com.stupid.newsapp.di

import com.stupid.newsapp.common.Utils
import com.stupid.newsapp.data.local.NewsDatabase
import com.stupid.newsapp.data.remote.NewsAPI
import com.stupid.newsapp.data.repository.NewsRepositoryImp
import com.stupid.newsapp.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNewsApi(): NewsAPI {
        return Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPI::class.java)
    }

    @Provides
    @Singleton
    @JvmSuppressWildcards
    fun provideNewsRepository(api: NewsAPI, db: NewsDatabase): Repository {
        return NewsRepositoryImp(api, db)
    }
}
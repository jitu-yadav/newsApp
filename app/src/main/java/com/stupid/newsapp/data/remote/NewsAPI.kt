package com.stupid.newsapp.data.remote

import com.stupid.newsapp.BuildConfig
import com.stupid.newsapp.common.Utils
import com.stupid.newsapp.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = Utils.PAGE_SIZE,
        @Query("country") country: String = Utils.DEFAULT_COUNTRY,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): NewsResponseDto

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("page") page: Int,
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int = Utils.PAGE_SIZE,
        @Query("country") country: String = Utils.DEFAULT_COUNTRY,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): NewsResponseDto
}
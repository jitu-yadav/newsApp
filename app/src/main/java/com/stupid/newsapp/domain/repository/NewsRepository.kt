package com.stupid.newsapp.domain.repository

import androidx.paging.PagingData
import com.stupid.newsapp.data.local.NewsEntity
import com.stupid.newsapp.domain.model.NewsArticle
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getTopHeadlines(): Flow<PagingData<NewsArticle>>

    fun searchForNews(query: String): Flow<PagingData<NewsArticle>>

    suspend fun bookmarkArticle(newsArticle: NewsArticle)

    suspend fun unBookmarkArticle(newsArticle: NewsArticle)

    fun getBookmarkedArticles(): Flow<List<NewsArticle>>

    fun getArticleDetailsById(id:String): Flow<NewsEntity>
}
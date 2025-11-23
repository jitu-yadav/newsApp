package com.stupid.newsapp.domain.repository

import androidx.paging.PagingData
import com.stupid.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getTopHeadlines(): Flow<PagingData<Article>>

    fun searchForNews(query: String): Flow<PagingData<Article>>

    suspend fun bookmarkArticle(article: Article)

    suspend fun unBookmarkArticle(article: Article)

    fun getBookmarkedArticles(): Flow<List<Article>>

}
package com.stupid.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.stupid.newsapp.common.Utils
import com.stupid.newsapp.data.local.NewsEntity
import com.stupid.newsapp.data.local.BookmarkNewsDatabase
import com.stupid.newsapp.data.mapper.toDomain
import com.stupid.newsapp.data.mapper.toEntity
import com.stupid.newsapp.data.remote.NewsArticleAPI
import com.stupid.newsapp.data.remote.paging.NewsPagingSource
import com.stupid.newsapp.data.remote.paging.SearchPagingSource
import com.stupid.newsapp.domain.model.NewsArticle
import com.stupid.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    val api: NewsArticleAPI,
    val db: BookmarkNewsDatabase
) : NewsRepository {

    override fun getTopHeadlines(): Flow<PagingData<NewsArticle>> {
        return Pager(
            config = PagingConfig(pageSize = Utils.PAGE_SIZE),
            pagingSourceFactory = { NewsPagingSource(api, db.newsDao()) }
        ).flow
    }

    override fun searchForNews(query: String): Flow<PagingData<NewsArticle>> {
        return Pager(
            config = PagingConfig(pageSize = Utils.PAGE_SIZE),
            pagingSourceFactory = { SearchPagingSource(api, db.newsDao(), query) }
        ).flow
    }

    override suspend fun bookmarkArticle(newsArticle: NewsArticle) {
        db.newsDao().bookmarkNewsArticle(newsArticle.toEntity())
    }

    override suspend fun unBookmarkArticle(newsArticle: NewsArticle) {
        db.newsDao().deleteNewsArticle(newsArticle.toEntity())
    }

    override fun getBookmarkedArticles(): Flow<List<NewsArticle>> {
        return db.newsDao().getNewsArticles().map {
            articles -> articles.map { it.toDomain() }
        }
    }

    override fun getArticleDetailsById(id: String): Flow<NewsEntity> {
        return db.newsDao().getNewsArticle(id)
    }
}
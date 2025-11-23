package com.stupid.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.stupid.newsapp.common.Utils
import com.stupid.newsapp.data.local.NewsDatabase
import com.stupid.newsapp.data.mapper.toDomain
import com.stupid.newsapp.data.mapper.toEntity
import com.stupid.newsapp.data.remote.NewsAPI
import com.stupid.newsapp.data.remote.paging.NewsPagingSource
import com.stupid.newsapp.data.remote.paging.SearchPagingSource
import com.stupid.newsapp.domain.model.Article
import com.stupid.newsapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImp @Inject constructor(
    val api: NewsAPI,
    val db: NewsDatabase
) : Repository {

    override fun getTopHeadlines(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = Utils.PAGE_SIZE),
            pagingSourceFactory = { NewsPagingSource(api, db.newsDao) }
        ).flow
    }

    override fun searchForNews(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = Utils.PAGE_SIZE),
            pagingSourceFactory = { SearchPagingSource(api, db.newsDao, query) }
        ).flow
    }

    override suspend fun bookmarkArticle(article: Article) {
        db.newsDao.bookmarkArticle(article.toEntity())
    }

    override suspend fun unBookmarkArticle(article: Article) {
        db.newsDao.deleteArticle(article.toEntity())
    }

    override fun getBookmarkedArticles(): Flow<List<Article>> {
        return db.newsDao.getArticles().map {
            articles -> articles.map { it.toDomain() }
        }
    }
}
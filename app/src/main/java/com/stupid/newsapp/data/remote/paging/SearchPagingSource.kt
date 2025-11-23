package com.stupid.newsapp.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.stupid.newsapp.data.local.NewsArticleDao
import com.stupid.newsapp.data.mapper.toDomain
import com.stupid.newsapp.data.remote.NewsArticleAPI
import com.stupid.newsapp.domain.model.NewsArticle
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class SearchPagingSource @Inject constructor(
    private val api: NewsArticleAPI,
    private val dao: NewsArticleDao,
    private val query: String
) : PagingSource<Int, NewsArticle>(
) {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsArticle> {
        val page = params.key ?: 1
        return try {
            val response = api.searchForNews(page = page, query = query)
            val bookMarks = dao.getNewsArticles().firstOrNull().orEmpty().associateBy { it.id }
            val articles = response.articles
                .map{it.toDomain()}
                .map { article ->
                    article.copy(isBookmarked = bookMarks.containsKey(article.id))
                }

            LoadResult.Page(
                data = articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (articles.isEmpty()) null else page + 1
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsArticle>): Int? {
        val anchorPos = state.anchorPosition ?: return null
        val closestPage = state.closestPageToPosition(anchorPos) ?: return null
        return closestPage.prevKey?.plus(1) ?: closestPage.nextKey?.minus(1)
    }
}
package com.stupid.newsapp.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.stupid.newsapp.data.local.ArticleDao
import com.stupid.newsapp.data.mapper.toDomain
import com.stupid.newsapp.data.remote.NewsAPI
import com.stupid.newsapp.domain.model.Article
import kotlinx.coroutines.flow.firstOrNull
import java.lang.Exception
import javax.inject.Inject

class NewsPagingSource @Inject constructor(
    private val api: NewsAPI,
    private val dao: ArticleDao
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
       val page = params.key ?: 1
        return try {
            val response = api.getTopHeadlines(page = page)
            val bookMarks = dao.getArticles().firstOrNull().orEmpty().associateBy { it.id }
            val articles = response.articles
                .map { it.toDomain() }
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

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}
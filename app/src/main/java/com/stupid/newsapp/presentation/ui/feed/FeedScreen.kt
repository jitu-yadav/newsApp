package com.stupid.newsapp.presentation.ui.feed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.stupid.newsapp.domain.model.NewsArticle
import com.stupid.newsapp.presentation.ui.common.ErrorItem
import com.stupid.newsapp.presentation.ui.common.LoadingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    viewModel: FeedViewModel = hiltViewModel(),
    onNewArticleClick: (NewsArticle) -> Unit
) {
    val pagingItems = viewModel.news.collectAsLazyPagingItems()

    val bookmarkIds by viewModel.bookmarkedIds.collectAsStateWithLifecycle()

    val isRefreshing = pagingItems.loadState.refresh is LoadState.Loading

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News Update") }
            )
        }
    ) { padding ->
        // 👇 Pull refresh indicator
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { pagingItems.refresh() },
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.padding(padding)
            ) {
                items(pagingItems.itemCount) { index ->
                    val article = pagingItems[index]
                    article?.let {
                        val isBookmarked = bookmarkIds.contains(article.id)

                        NewsCardItem(
                            newsArticle = article,
                            isBookmarked = isBookmarked,
                            onClick = { onNewArticleClick(article) },
                            onBookmarkClick = {
                                val article = it.apply {
                                    it.isBookmarked = bookmarkIds.contains(it.id)
                                }
                                viewModel.toggleBookmark(article)
                            }
                        )
                    }
                }

                // Show loading states at bottom if needed
                pagingItems.apply {
                    when (loadState.append) {
                        is LoadState.Loading -> item { LoadingItem() }
                        is LoadState.Error -> item {
                            ErrorItem("Error loading more", onRetry = { retry() })
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

}
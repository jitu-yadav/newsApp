package com.stupid.newsapp.presentation.ui.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.stupid.newsapp.domain.model.NewsArticle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    viewModel: FeedViewModel = hiltViewModel(),
    onNewArticleClick: (NewsArticle) -> Unit
) {
    val pagingItems = viewModel.news.collectAsLazyPagingItems()

    val isRefreshing = pagingItems.loadState.refresh is LoadState.Loading

    // 👇 Pull refresh indicator
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { pagingItems.refresh() },
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.padding(8.dp)
        ) {
            items(pagingItems.itemCount) { index ->
                val article = pagingItems[index]
                if (article != null)
                    NewsCardItem(
                        article,
                        onClick = { onNewArticleClick(article) },
                        onBookmarkClick = { viewModel.toggleBookmark(article) }
                    )
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

@Composable
fun LoadingItem() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorItem(message: String, onRetry: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(message, color = Color.Red)
        Spacer(Modifier.height(8.dp))
        Button(onClick = onRetry) { Text("Retry") }
    }
}
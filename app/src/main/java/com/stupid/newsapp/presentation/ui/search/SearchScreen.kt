package com.stupid.newsapp.presentation.ui.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.stupid.newsapp.domain.model.NewsArticle
import com.stupid.newsapp.presentation.ui.feed.ErrorItem
import com.stupid.newsapp.presentation.ui.feed.LoadingItem
import com.stupid.newsapp.presentation.ui.feed.NewsCardItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onArticleClick: (NewsArticle) -> Unit,
) {
    val searchQuery = remember { mutableStateOf("") }
    val results = viewModel.results.collectAsLazyPagingItems()

    val isRefreshing = results.loadState.refresh is LoadState.Loading


    Scaffold(topBar = {
        TopAppBar(
            title = {
                TextField(
                    value = searchQuery.value,
                    onValueChange = {
                        searchQuery.value = it
                        viewModel.updateQuery(it)
                    },
                    placeholder = { Text("Search news…") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
                )
            }
        )
    }) { padding ->
        // 👇 Pull refresh indicator
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { results.refresh() },
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(results.itemCount) { index ->
                    results[index]?.let { article ->
                        NewsCardItem(
                            newsArticle = article,
                            onClick = { onArticleClick(article) },
                            onBookmarkClick = { viewModel.toggleBookmark(article) }
                        )
                    }
                }

                results.apply {
                    when (loadState.append) {
                        is LoadState.Loading -> item { LoadingItem() }
                        is LoadState.Error -> item {
                            ErrorItem(
                                message = "Failed loading more",
                                onRetry = { retry() }
                            )
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}

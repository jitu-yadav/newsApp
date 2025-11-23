package com.stupid.newsapp.presentation.ui.feed

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.stupid.newsapp.domain.model.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    viewModel: FeedViewModel = hiltViewModel(),
    onNewArticleClick: (Article) -> Unit,
    onBookmarkClick: () -> Unit,
    onSearchClick:() -> Unit
) {
    val pagingItems = viewModel.news.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "News") },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }

    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(pagingItems.itemCount){index ->
                val article = pagingItems[index]
                if (article != null)
                    NewsCardItem(
                        article,
                        onClick = {onNewArticleClick(article)},
                        onBookmarkClick = {viewModel.toggleBookmark(article)}
                    )
            }
        }
    }

}
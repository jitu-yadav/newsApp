package com.stupid.newsapp.presentation.ui.newsdetails

import android.annotation.SuppressLint
import android.content.Intent
import android.webkit.WebView
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.core.net.toUri


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    viewModel: NewsDetailViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
) {
    val context = LocalContext.current
    val articleDetail = viewModel.newsArticle ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(articleDetail.title ?: "Article") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                },
                actions = {
                    // Share
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, articleDetail.url)
                        }
                        context.startActivity(Intent.createChooser(intent, "Share via"))
                    }) {
                        Icon(Icons.Default.Share, null)
                    }

                    // Bookmark Toggle
                    IconButton(onClick = {
                        articleDetail.let { viewModel.toggleBookmark(it) }
                    }) {
                        Icon(
                            if (articleDetail.isBookmarked)
                                Icons.Default.Bookmark
                            else Icons.Default.BookmarkBorder,
                            contentDescription = null
                        )
                    }

                    // Open in Browser
                    IconButton(onClick = {
                        val browserIntent = Intent(Intent.ACTION_VIEW, articleDetail.url?.toUri())
                        context.startActivity(browserIntent)
                    }) {
                        Icon(Icons.Default.OpenInBrowser, null)
                    }
                }
            )
        }
    ) { padding ->
        if (viewModel.showNativeDetail) {
            // Option A: Native HTML content from API
            ArticleNativeContent(
                html = articleDetail.content,
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            )
        } else {
            // Option B: WebView
            articleDetail.url?.let {
                ArticleWebView(
                    it,
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                )
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ArticleWebView(
    url: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.domStorageEnabled = true
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        },
        modifier = modifier
    )
}

@Composable
fun ArticleNativeContent(html: String?, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                html?.let { text = HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY) }
                textSize = 16f
                setPadding(16, 16, 16, 16)
            }
        },
        modifier = modifier
    )
}


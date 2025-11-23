package com.stupid.newsapp.domain.model

data class Article(
    val id: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    val isBookmarked: Boolean = false
)
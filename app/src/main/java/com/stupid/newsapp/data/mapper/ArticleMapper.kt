package com.stupid.newsapp.data.mapper

import com.stupid.newsapp.data.local.ArticleEntity
import com.stupid.newsapp.data.remote.dto.ArticleDto
import com.stupid.newsapp.domain.model.Article

fun ArticleDto.toDomain() : Article {
    val id = url.hashCode().toString()
    return Article(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        url = url,
        urlToImage = urlToImage,
        title = title,
        source = source?.name,
    )
}

fun Article.toEntity() : ArticleEntity {
    return ArticleEntity(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        url = url,
        urlToImage = urlToImage,
        title = title,
        source = source
    )
}

fun ArticleEntity.toDomain() : Article {
    return Article(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        url = url,
        urlToImage = urlToImage,
        title = title,
        source = source,
        isBookmarked = true
    )
}
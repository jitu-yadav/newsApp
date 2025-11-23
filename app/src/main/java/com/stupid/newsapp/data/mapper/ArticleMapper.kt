package com.stupid.newsapp.data.mapper

import com.stupid.newsapp.data.local.NewsEntity
import com.stupid.newsapp.data.remote.dto.NewsArticleDto
import com.stupid.newsapp.domain.model.NewsArticle
import java.util.UUID

fun NewsArticleDto.toDomain() : NewsArticle {
    val id = url?.hashCode()?.toString() ?: UUID.randomUUID().toString()
    return NewsArticle(
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

fun NewsArticle.toEntity() : NewsEntity {
    return NewsEntity(
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

fun NewsEntity.toDomain() : NewsArticle {
    return NewsArticle(
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
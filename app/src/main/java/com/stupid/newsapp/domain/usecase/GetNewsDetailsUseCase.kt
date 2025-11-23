package com.stupid.newsapp.domain.usecase

import com.stupid.newsapp.data.mapper.toDomain
import com.stupid.newsapp.domain.model.NewsArticle
import com.stupid.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNewsDetailsUseCase(
    private val repo: NewsRepository
) {
    operator fun invoke(id: String) : Flow<NewsArticle> {
        return repo.getArticleDetailsById(id).map { it.toDomain() }
    }
}
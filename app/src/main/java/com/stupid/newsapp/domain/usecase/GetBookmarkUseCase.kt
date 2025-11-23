package com.stupid.newsapp.domain.usecase

import com.stupid.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class GetBookmarkUseCase @Inject constructor(
    private val repo: NewsRepository
) {
    operator fun invoke() = repo.getBookmarkedArticles()
}
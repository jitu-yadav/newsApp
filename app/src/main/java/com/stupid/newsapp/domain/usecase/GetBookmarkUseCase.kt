package com.stupid.newsapp.domain.usecase

import com.stupid.newsapp.domain.repository.Repository
import javax.inject.Inject

class GetBookmarkUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke() = repository.getBookmarkedArticles()
}
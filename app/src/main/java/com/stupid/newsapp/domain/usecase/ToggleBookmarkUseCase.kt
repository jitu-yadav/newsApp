package com.stupid.newsapp.domain.usecase

import com.stupid.newsapp.domain.model.NewsArticle
import com.stupid.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(
    private val repo: NewsRepository
) {
    suspend operator fun invoke(newsArticle: NewsArticle) {
        if (newsArticle.isBookmarked) {
            repo.unBookmarkArticle(newsArticle)
        } else {
            repo.bookmarkArticle(newsArticle)
        }
    }
}
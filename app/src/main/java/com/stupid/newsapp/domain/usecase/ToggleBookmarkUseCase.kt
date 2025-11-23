package com.stupid.newsapp.domain.usecase

import com.stupid.newsapp.domain.model.Article
import com.stupid.newsapp.domain.repository.Repository
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(article: Article) {
        if (article.isBookmarked) {
            repository.unBookmarkArticle(article)
        } else {
            repository.bookmarkArticle(article)
        }
    }
}
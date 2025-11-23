package com.stupid.newsapp.domain.usecase

import com.stupid.newsapp.domain.repository.Repository
import javax.inject.Inject

class SearchArticlesUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(query: String) = repository.searchForNews(query)
}
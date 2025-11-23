package com.stupid.newsapp.domain.usecase

import com.stupid.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkedIdsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<List<String>> {
        return repository.getBookmarksIds()
    }
}
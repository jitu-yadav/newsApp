package com.stupid.newsapp.domain.usecase

import androidx.paging.PagingData
import com.stupid.newsapp.domain.model.Article
import com.stupid.newsapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsArticleUseCase @Inject constructor(
    private val repository: Repository
) {

    operator fun invoke(): Flow<PagingData<Article>> {
        return repository.getTopHeadlines()
    }
}
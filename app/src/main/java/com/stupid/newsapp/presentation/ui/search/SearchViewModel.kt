package com.stupid.newsapp.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.stupid.newsapp.domain.model.NewsArticle
import com.stupid.newsapp.domain.usecase.SearchArticlesUseCase
import com.stupid.newsapp.domain.usecase.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchArticlesUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val query = MutableStateFlow("")

    val results = query
        .debounce(300)
        .filter { it.isNotBlank() }
        .flatMapLatest { searchUseCase(it) }
        .cachedIn(viewModelScope)

    fun updateQuery(newQuery: String) {
        query.value = newQuery
    }

    fun toggleBookmark(newsArticle: NewsArticle) {
        viewModelScope.launch {
            toggleBookmarkUseCase(newsArticle)
        }
    }
}

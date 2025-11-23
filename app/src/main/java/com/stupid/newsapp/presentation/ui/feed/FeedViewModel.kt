package com.stupid.newsapp.presentation.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.stupid.newsapp.domain.model.NewsArticle
import com.stupid.newsapp.domain.usecase.GetBookmarkedIdsUseCase
import com.stupid.newsapp.domain.usecase.GetNewsArticleUseCase
import com.stupid.newsapp.domain.usecase.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    getNewsArticleUseCase: GetNewsArticleUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
    private val getBookmarkedIdsUseCase: GetBookmarkedIdsUseCase
) : ViewModel() {
    val news = getNewsArticleUseCase()
        .cachedIn(viewModelScope)
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )

    // Expose the live stream of bookmarked IDs
    val bookmarkedIds = getBookmarkedIdsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptySet()
        )

    fun toggleBookmark(newsArticle: NewsArticle) {
        viewModelScope.launch {
            toggleBookmarkUseCase(newsArticle)
        }
    }

}
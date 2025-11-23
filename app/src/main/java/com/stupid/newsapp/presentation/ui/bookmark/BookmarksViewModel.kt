package com.stupid.newsapp.presentation.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stupid.newsapp.domain.model.NewsArticle
import com.stupid.newsapp.domain.usecase.GetBookmarkUseCase
import com.stupid.newsapp.domain.usecase.GetBookmarkedIdsUseCase
import com.stupid.newsapp.domain.usecase.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    getBookmarkUseCase: GetBookmarkUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
    private val bookmarkedIdsUseCase: GetBookmarkedIdsUseCase
): ViewModel() {

    val bookmarks = getBookmarkUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Expose the live stream of bookmarked IDs
    val bookmarkedIds = bookmarkedIdsUseCase()
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
package com.stupid.newsapp.presentation.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.stupid.newsapp.domain.model.Article
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
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {
    val news = getNewsArticleUseCase()
        .cachedIn(viewModelScope)
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )

    fun toggleBookmark(article: Article) {
        viewModelScope.launch {
            toggleBookmarkUseCase(article)
        }
    }

}
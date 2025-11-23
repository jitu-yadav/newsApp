package com.stupid.newsapp.presentation.ui.newsdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stupid.newsapp.common.Utils
import com.stupid.newsapp.domain.model.NewsArticle
import com.stupid.newsapp.domain.usecase.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val newsArticle = savedStateHandle.get<NewsArticle>(Utils.ARTICLE)

    val showNativeDetail: Boolean = !newsArticle?.content.isNullOrBlank()
    fun toggleBookmark(newsArticle: NewsArticle) {
        viewModelScope.launch {
            toggleBookmarkUseCase(newsArticle)
        }
    }


}
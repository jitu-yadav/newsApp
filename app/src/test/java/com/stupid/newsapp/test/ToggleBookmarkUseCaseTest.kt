package com.stupid.newsapp.test

import com.stupid.newsapp.data.mapper.toDomain
import com.stupid.newsapp.domain.model.NewsArticle
import com.stupid.newsapp.domain.repository.NewsRepository
import com.stupid.newsapp.domain.usecase.ToggleBookmarkUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ToggleBookmarkUseCaseTest {

    private val repository: NewsRepository = mockk(relaxed = true)
    private lateinit var toggleBookmarkUseCase: ToggleBookmarkUseCase

    @Before
    fun setUp() {
        toggleBookmarkUseCase = ToggleBookmarkUseCase(repository)
    }

    @Test
    fun `invoke calls unBookmarkArticle when article is already bookmarked`() = runTest {
        // Arrange
        val article = createDummyArticle().copy(isBookmarked = true)

        // Act
        toggleBookmarkUseCase(article)

        // Assert
        coVerify(exactly = 1) { repository.unBookmarkArticle(article) }
        coVerify(exactly = 0) { repository.bookmarkArticle(any()) }
    }

    @Test
    fun `invoke calls bookmarkArticle when article is NOT bookmarked`() = runTest {
        // Arrange
        val article = createDummyArticle().copy(isBookmarked = false)

        // Act
        toggleBookmarkUseCase(article)

        // Assert
        coVerify(exactly = 1) { repository.bookmarkArticle(article) }
        coVerify(exactly = 0) { repository.unBookmarkArticle(any()) }
    }

    // Helper to create a dummy article
    private fun createDummyArticle(): NewsArticle {
        return NewsArticle(
            id = "1",
            url = "http://test.com",
            title = "Test Title",
            source = "CNN",
            content = "",
            description = "",
            publishedAt = "",
            urlToImage = "",
            author = "",
            isBookmarked = false // Default
        )
    }
}

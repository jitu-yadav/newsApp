package com.stupid.newsapp.test

import androidx.compose.foundation.layout.size
import com.stupid.newsapp.data.local.BookmarkNewsDatabase
import com.stupid.newsapp.data.local.NewsArticleDao
import com.stupid.newsapp.data.local.NewsEntity
import com.stupid.newsapp.data.mapper.toEntity
import com.stupid.newsapp.data.remote.NewsArticleAPI
import com.stupid.newsapp.data.repository.NewsRepositoryImpl
import com.stupid.newsapp.domain.model.NewsArticle
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.internalSubstitute
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class NewsRepositoryImplTest {

    private val newsApi: NewsArticleAPI = mockk()
    private val newsDatabase: BookmarkNewsDatabase = mockk()
    private val newsDao: NewsArticleDao = mockk()

    private lateinit var repository: NewsRepositoryImpl

    @Before
    fun setUp() {
        every { newsDatabase.newsDao() } returns newsDao
        repository = NewsRepositoryImpl(newsApi, newsDatabase)
    }

    @Test
    fun `bookmarkArticle calls dao bookmarkNewsArticle`() = runTest {
        val article = NewsArticle(
            id = "123", url = "http://test.com", title = "Test", source = "CNN",
            content = "", description = "", publishedAt = "", urlToImage = "", author = ""
        )

        coEvery { newsDao.bookmarkNewsArticle(any()) } just runs

        repository.bookmarkArticle(article)

        coVerify { newsDao.bookmarkNewsArticle(any()) }
    }

    @Test
    fun `unBookmarkArticle calls dao deleteNewsArticle`() = runTest {
        val article = NewsArticle(
            id = "123", url = "http://test.com", title = "Test", source = "CNN",
            content = "", description = "", publishedAt = "", urlToImage = "", author = ""
        )

        coEvery { newsDao.deleteNewsArticle(any()) } just runs

        repository.unBookmarkArticle(article)

        coVerify { newsDao.deleteNewsArticle(any()) }
    }

    @Test
    fun `getBookmarksIds returns mapped flow from dao`() = runTest {
        // FIX: Mock getBookmarksIds() directly.
        // Since newsDao is a mock, the default implementation in the interface is NOT executed.
        // We just need to verify the repository returns what the DAO gives it.
        val expectedIds = listOf("url1", "url2")
        every { newsDao.getBookmarksIds() } returns flowOf(expectedIds)

        // Act
        val result = repository.getBookmarksIds().first()

        // Assert
        assertEquals(2, result.size)
        assertEquals("url1", result[0])
    }

    @Test
    fun `getArticleDetailsById returns article when found`() = runTest {
        val entity = NewsEntity(
            id = "123", url = "url1", title = "T", source = "S",
            publishedAt = "", author = "", description = "", content = "", urlToImage = ""
        )

        // Mock returning a Flow containing the entity
        every { newsDao.getNewsArticle("123") } returns flowOf(entity)

        val result = repository.getArticleDetailsById("123").first()

        assertEquals("123", result?.id)
        assertEquals("T", result?.title)
    }

    @Test
    fun `getArticleDetailsById returns null when flow is empty`() = runTest {
        // Mock returning an empty Flow (simulating Room not finding anything)
        // Note: Since your DAO return type is Flow<NewsEntity> (not nullable),
        // an empty flow is the standard way Room represents "not found" before converting to List.
        // However, if using .firstOrNull() in repo, this handles it.
        every { newsDao.getNewsArticle("999") } returns flowOf()

        // We use firstOrNull() here because the flow itself is empty
        val result = repository.getArticleDetailsById("999").firstOrNull()

        assertNull(result)
    }
}


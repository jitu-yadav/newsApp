package com.stupid.newsapp.test

import com.google.common.truth.Truth.assertThat
import com.stupid.newsapp.data.remote.NewsArticleAPI
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: NewsArticleAPI

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // Point Retrofit to local mock server
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsArticleAPI::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getTopHeadlines returns valid response when API call is successful`() = runTest {
        // 1. Prepare the Mock JSON Response
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(
                """
                {
                    "status": "ok",
                    "totalResults": 1,
                    "articles": [
                        {
                            "source": { "id": "bbc-news", "name": "BBC News" },
                            "author": "BBC Sport",
                            "title": "Test Article Title",
                            "description": "Test Description",
                            "url": "https://www.bbc.co.uk/news/test",
                            "urlToImage": "https://image.url",
                            "publishedAt": "2024-05-22T12:00:00Z",
                            "content": "Test content"
                        }
                    ]
                }
            """.trimIndent()
            )

        mockWebServer.enqueue(mockResponse)

        // 2. Execute the API call
        val response = apiService.getTopHeadlines(page = 1, apiKey = "123")

        // 3. Verify assertions
        assertThat(response.status).isEqualTo("ok")
        assertThat(response.articles).hasSize(1)
        assertThat(response.articles[0].title).isEqualTo("Test Article Title")
        assertThat(response.articles[0].source?.name).isEqualTo("BBC News")
    }

    @Test
    fun `getTopHeadlines sends correct parameters`() = runTest {
        // Enqueue a dummy response just to prevent a crash
        mockWebServer.enqueue(MockResponse().setBody("{}"))

        // Call the API
        apiService.getTopHeadlines(page = 2, apiKey = "test_key")

        // Verify the REQUEST sent to the server
        val request = mockWebServer.takeRequest()

        assertThat(request.path).contains("page=2")
        assertThat(request.path).contains("apiKey=test_key")
    }

    @Test
    fun `searchForNews sends correct query parameters`() = runTest {
        // Enqueue dummy response
        mockWebServer.enqueue(MockResponse().setBody("{}"))

        // Execute search call
        apiService.searchForNews(query = "bitcoin", page = 1, apiKey = "123")

        val request = mockWebServer.takeRequest()

        // Verify that the query param 'q' was sent correctly
        assertThat(request.path).contains("q=bitcoin")
        assertThat(request.path).contains("page=1")
        assertThat(request.path).contains("apiKey=123")
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `getTopHeadlines throws HttpException when server returns 404`() = runTest {
        // Prepare a 404 Not Found response
        val mockResponse = MockResponse()
            .setResponseCode(404)
            .setBody("Not Found")

        mockWebServer.enqueue(mockResponse)

        // This call should throw an exception
        apiService.getTopHeadlines(page = 1, apiKey = "123")
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `getTopHeadlines throws HttpException when server returns 500`() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(500)
            .setBody("Internal Server Error")

        mockWebServer.enqueue(mockResponse)

        apiService.getTopHeadlines(page = 1, apiKey = "123")
    }

    @Test
    fun `getTopHeadlines parses empty article list correctly`() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                    "status": "ok",
                    "totalResults": 0,
                    "articles": []
                }
            """.trimIndent())

        mockWebServer.enqueue(mockResponse)

        val response = apiService.getTopHeadlines(page = 1, apiKey = "123")

        assertThat(response.status).isEqualTo("ok")
        assertThat(response.articles).isEmpty()
    }

    // --- NEW TEST CASES ADDED BELOW ---

    @Test
    fun `getTopHeadlines uses GET http method`() = runTest {
        mockWebServer.enqueue(MockResponse().setBody("{}"))

        apiService.getTopHeadlines(page = 1, apiKey = "123")

        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
    }

    @Test(expected = com.google.gson.JsonSyntaxException::class)
    fun `getTopHeadlines throws JsonSyntaxException when json is malformed`() = runTest {
        // Prepare a response that is NOT valid JSON
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("This is not valid json { } [ ]")

        mockWebServer.enqueue(mockResponse)

        // This should crash with a syntax exception
        apiService.getTopHeadlines(page = 1, apiKey = "123")
    }

    @Test
    fun `getTopHeadlines handles null fields in json response`() = runTest {
        // JSON where 'description' and 'author' are explicitly null
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                    "status": "ok",
                    "totalResults": 1,
                    "articles": [
                        {
                            "source": { "id": "cnn", "name": "CNN" },
                            "author": null,
                            "title": "Title",
                            "description": null,
                            "url": "url",
                            "urlToImage": "img",
                            "publishedAt": "2024-01-01T00:00:00Z",
                            "content": "content"
                        }
                    ]
                }
            """.trimIndent())

        mockWebServer.enqueue(mockResponse)

        val response = apiService.getTopHeadlines(page = 1, apiKey = "123")

        // Verify that the object was created but fields are null
        assertThat(response.articles[0].author).isNull()
        assertThat(response.articles[0].description).isNull()
        // Verify non-null fields still work
        assertThat(response.articles[0].title).isEqualTo("Title")
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `getTopHeadlines throws HttpException on 401 unauthorized`() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(401)
            .setBody("""{"status": "error", "code": "apiKeyMissing", "message": "Your API key is missing."}""")

        mockWebServer.enqueue(mockResponse)

        apiService.getTopHeadlines(page = 1, apiKey = "wrong_key")
    }
}

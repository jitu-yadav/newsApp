package com.stupid.newsapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Dao
interface NewsArticleDao {

    @Query("SELECT * FROM bookmarks_articles")
    fun getNewsArticles(): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsArticles(articles: List<NewsEntity>)

    @Delete
    suspend fun deleteNewsArticle(article: NewsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bookmarkNewsArticle(article: NewsEntity)

    @Query("SELECT * FROM bookmarks_articles where id = :id")
    fun getNewsArticle(id: String): Flow<NewsEntity>

    @Query("SELECT id FROM bookmarks_articles")
    fun getBookmarksIds(): Flow<List<String>> {
        return getNewsArticles().map { articles ->
            articles.map { it.id }
        }
    }
}
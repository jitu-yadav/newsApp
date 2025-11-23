package com.stupid.newsapp.common

import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    const val BASE_URL = "https://newsapi.org/"
    const val DATABASE_NAME = "news_database"
    const val PAGE_SIZE = 20
    const val DEFAULT_COUNTRY = "us"


    fun formatDate(dateString: String): String {
        return try {
            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val output = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date = input.parse(dateString)
            output.format(date!!)
        } catch (e: Exception) {
            ""
        }
    }
}
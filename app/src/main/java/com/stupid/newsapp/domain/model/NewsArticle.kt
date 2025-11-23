package com.stupid.newsapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsArticle(
    val id: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    val isBookmarked: Boolean = false
) : Parcelable
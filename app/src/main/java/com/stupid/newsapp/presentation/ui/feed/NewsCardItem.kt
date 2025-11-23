package com.stupid.newsapp.presentation.ui.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.stupid.newsapp.common.Utils
import com.stupid.newsapp.domain.model.NewsArticle

@Composable
fun NewsCardItem(
    newsArticle: NewsArticle,
    onClick: (newsArticle: NewsArticle) -> Unit,
    onBookmarkClick: (newsArticle: NewsArticle) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(newsArticle) },

        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            newsArticle.urlToImage?.takeIf { it.isNotEmpty() }?.let { imageUrl ->
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)     // optional
                            .build(),
                        contentDescription = newsArticle.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    // BOOKMARK ICON OVER IMAGE (Top Right Corner)
                    IconButton(
                        onClick = { onBookmarkClick(newsArticle) },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = if (newsArticle.isBookmarked)
                                Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Title
            Text(
                text = newsArticle.title?: "",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Short Description (optional)
            newsArticle.description?.takeIf { it.isNotBlank() }?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            // Source + Published Date Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = newsArticle.source ?: "",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.DarkGray
                )
                Text(
                    text = newsArticle.publishedAt?.let { Utils.formatDate(it) } ?: "",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.DarkGray
                )
            }
        }

    }
}
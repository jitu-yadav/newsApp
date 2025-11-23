package com.stupid.newsapp.presentation.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Feed : BottomNavItem("feed", "Home", Icons.Default.Home)
    object Bookmarks : BottomNavItem("bookmarks", "Bookmarks", Icons.Default.Bookmark)
    object Search : BottomNavItem("search", "Search", Icons.Default.Search)
}
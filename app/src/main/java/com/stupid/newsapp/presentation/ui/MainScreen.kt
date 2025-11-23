package com.stupid.newsapp.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.vector.ImageVector
import com.stupid.newsapp.presentation.ui.navigation.NavRoutes

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Home : BottomNavItem(
        route = NavRoutes.FEED,
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )

    object Bookmarks : BottomNavItem(
        route = NavRoutes.BOOKMARKS,
        title = "Bookmarks",
        selectedIcon = Icons.Filled.Bookmark,
        unselectedIcon = Icons.Outlined.Bookmark
    )
}
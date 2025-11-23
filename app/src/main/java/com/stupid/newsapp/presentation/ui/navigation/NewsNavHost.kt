package com.stupid.newsapp.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stupid.newsapp.presentation.ui.feed.FeedScreen

@Composable
fun NewsNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.FEED
    ) {
        composable(NavRoutes.FEED) {
            FeedScreen(
                onNewArticleClick = {
                    navController.navigate(NavRoutes.DETAILS + "/${it.url}")
                },
                onBookmarkClick = {
                    navController.navigate(NavRoutes.BOOKMARKS)
                },
                onSearchClick = {
                    navController.navigate(NavRoutes.SEARCH)
                }
            )
        }

    }

}
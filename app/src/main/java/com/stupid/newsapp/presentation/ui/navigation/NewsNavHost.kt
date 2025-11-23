package com.stupid.newsapp.presentation.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stupid.newsapp.common.Utils
import com.stupid.newsapp.domain.model.NewsArticle
import com.stupid.newsapp.presentation.ui.bookmark.BookmarksScreen
import com.stupid.newsapp.presentation.ui.feed.FeedScreen
import com.stupid.newsapp.presentation.ui.newsdetails.NewsDetailScreen
import com.stupid.newsapp.presentation.ui.search.SearchScreen

@Composable
fun NewsNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavRoutes.FEED
    ) {
        composable(NavRoutes.FEED) {
            FeedScreen(
                onNewArticleClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(Utils.ARTICLE, it)
                    navController.navigate(NavRoutes.DETAILS)
                },
            )
        }

        composable(NavRoutes.SEARCH) {
            SearchScreen(
                onArticleClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(Utils.ARTICLE, it)
                    navController.navigate(NavRoutes.DETAILS)
                })
        }

        composable(NavRoutes.BOOKMARKS) {
            BookmarksScreen(
                onArticleClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(Utils.ARTICLE, it)
                    navController.navigate(NavRoutes.DETAILS)
                }
            )
        }

        // Add the Details Route
        composable(route = NavRoutes.DETAILS) {
                // Retrieve the Article object from the PREVIOUS back stack entry (the one that sent us here)
                // Note: You might need to cast the result if the generic inference fails
                val article = navController.previousBackStackEntry?.savedStateHandle?.get<NewsArticle>(Utils.ARTICLE)

            article?.let { articleDetail ->
                NewsDetailScreen(
                    articleDetail,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
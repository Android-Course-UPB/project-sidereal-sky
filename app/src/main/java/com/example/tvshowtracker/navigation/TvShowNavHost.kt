package com.example.tvshowtracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tvshowtracker.ui.screens.DetailsScreen
import com.example.tvshowtracker.ui.screens.HomeScreen
import com.example.tvshowtracker.ui.screens.SearchScreen
import com.example.tvshowtracker.ui.screens.SettingsScreen

@Composable
fun TvShowNavHost(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("search") {
            SearchScreen(navController = navController, onShowSelected = { showId ->
                navController.navigate("details/$showId")
            })
        }
        composable("settings") {
            SettingsScreen(
                navController = navController,
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange
            )
        }
        composable(
            route = "details/{showId}",
            arguments = listOf(navArgument("showId") { type = NavType.IntType })
        ) { backStackEntry ->
            val showId = backStackEntry.arguments?.getInt("showId") ?: return@composable
            DetailsScreen(showId = showId, navController = navController)
        }
    }
}
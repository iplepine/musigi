package com.zs.jyoon.musigi

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.zs.jyoon.album.ui.AlbumScreen
import com.zs.jyoon.library.LibraryScreen

fun NavGraphBuilder.addLibraryScreen(navController: NavHostController) {
    composable(
        route = "library",
    ) {
        LibraryScreen(navController)
    }
}

fun NavGraphBuilder.addAlbumScreen(navController: NavHostController) {
    composable(
        route = "album/{artistName}/{albumId}",
        arguments = listOf(
            navArgument("artistName") { type = NavType.StringType },
            navArgument("albumId") { type = NavType.StringType }
        )
    ) {
        AlbumScreen(navController = navController)
    }
}
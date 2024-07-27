package com.gyub.movieticket.movie.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gyub.movieticket.movie.ui.MovieRoute
import com.gyub.movieticket.navigation.MainRoute

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */
fun NavController.navigateToMovie() {
    navigate(MainRoute.Movie)
}

fun NavGraphBuilder.movieGraph(
    innerPadding: PaddingValues,
    onMovieClick: (String) -> Unit,
) {
    composable<MainRoute.Movie> {
        MovieRoute(
            innerPadding = innerPadding,
            onMovieClick = onMovieClick
            )
    }
}
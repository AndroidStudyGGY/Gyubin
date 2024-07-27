package com.gyub.movieticket.movie.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.gyub.movieticket.movie.model.ReservationResultInfoUiModel
import com.gyub.movieticket.movie.ui.MovieDetailRoute
import com.gyub.movieticket.navigation.MainRoute

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/27
 */
fun NavController.navigateToMovieDetail(movieCode: String) {
    navigate(MainRoute.MovieDetail(movieCode))
}

fun NavGraphBuilder.movieDetailGraph(
    innerPadding: PaddingValues,
    moveReservationResult: (ReservationResultInfoUiModel) -> Unit,
) {
    composable<MainRoute.MovieDetail> { navBackStackEntry ->
        val movieCode = navBackStackEntry.toRoute<MainRoute.MovieDetail>().movieCode

        MovieDetailRoute(
            innerPadding = innerPadding,
            movieCode = movieCode,
            moveReservationResult = moveReservationResult
        )
    }
}
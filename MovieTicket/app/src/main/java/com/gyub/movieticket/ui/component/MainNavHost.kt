package com.gyub.movieticket.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.gyub.movieticket.MainNavigator
import com.gyub.movieticket.movie.navigation.movieDetailGraph
import com.gyub.movieticket.movie.navigation.movieGraph
import com.gyub.movieticket.movie.navigation.reservationResultGraph
import com.gyub.movieticket.navigation.MainRoute

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */
@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    padding: PaddingValues,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        NavHost(
            navController = navigator.navController,
            startDestination = MainRoute.Movie
        ) {
            movieGraph(
                padding,
                navigator::navigateToMovieDetail
            )

            movieDetailGraph(
                padding,
                navigator::navigateToReservationResult
            )

            reservationResultGraph(
                padding,
            )
        }
    }
}
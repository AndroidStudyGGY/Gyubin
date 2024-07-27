package com.gyub.movieticket

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gyub.movieticket.movie.model.ReservationResultInfoUiModel
import com.gyub.movieticket.movie.navigation.navigateToMovie
import com.gyub.movieticket.movie.navigation.navigateToMovieDetail
import com.gyub.movieticket.movie.navigation.navigateToReservationResult

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */
internal class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    fun navigateToMovie() {
        navController.navigateToMovie()
    }

    fun navigateToMovieDetail(code: String) {
        navController.navigateToMovieDetail(code)
    }

    fun navigateToReservationResult(reservedInfo: ReservationResultInfoUiModel) {
        navController.navigateToReservationResult(reservedInfo)
    }

}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
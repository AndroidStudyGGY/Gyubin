package com.gyub.movieticket.movie.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.gyub.movieticket.movie.model.ReservationResultInfoUiModel
import com.gyub.movieticket.movie.ui.ReservationResultRoute
import com.gyub.movieticket.navigation.MainRoute

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/27
 */
fun NavController.navigateToReservationResult(reservedInfo: ReservationResultInfoUiModel) {
    navigate(
        MainRoute.ReservationResult(
            movieName = reservedInfo.movieName,
            movieGenre = reservedInfo.movieGenre,
            reservedCount = reservedInfo.reservedCount,
            totalPrice = reservedInfo.totalPrice

        )
    )
}

fun NavGraphBuilder.reservationResultGraph(
    innerPadding: PaddingValues,
) {
    composable<MainRoute.ReservationResult> { navBackStackEntry ->
        val result = navBackStackEntry.toRoute<MainRoute.ReservationResult>()
        val reservedInfo = ReservationResultInfoUiModel(
            movieName = result.movieName,
            movieGenre = result.movieGenre,
            reservedCount = result.reservedCount,
            totalPrice = result.totalPrice
        )

        ReservationResultRoute(innerPadding = innerPadding, reservedInfo = reservedInfo)
    }
}
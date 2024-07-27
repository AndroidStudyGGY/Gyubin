package com.gyub.movieticket.navigation

import kotlinx.serialization.Serializable

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */
sealed interface MainRoute {
    @Serializable
    data object Movie : MainRoute

    @Serializable
    data class MovieDetail(val movieCode: String) : MainRoute

    @Serializable
    data class ReservationResult(
        val movieName: String = "",
        val movieGenre: String = "",
        val reservedCount: Int = 0,
        val totalPrice: Int = 0,
    ) : MainRoute
}
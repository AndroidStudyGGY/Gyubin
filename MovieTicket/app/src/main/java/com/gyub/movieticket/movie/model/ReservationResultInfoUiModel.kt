package com.gyub.movieticket.movie.model

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/27
 */
data class ReservationResultInfoUiModel(
    val movieName: String = "",
    val movieGenre: String = "",
    val reservedCount: Int = 0,
    val totalPrice: Int = 0,
)
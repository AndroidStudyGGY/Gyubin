package com.gyub.movieticket.movie.model

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/27
 */
data class ReservationInfoUiState(
    val date: String = "",
    val selectedTime: Int = 0,
    val reservationCount: Int = 1,
    val totalPrice: Int = 7000,
)
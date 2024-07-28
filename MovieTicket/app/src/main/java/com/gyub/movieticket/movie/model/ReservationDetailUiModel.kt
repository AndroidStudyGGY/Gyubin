package com.gyub.movieticket.movie.model

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/27
 */
data class ReservationDetailUiModel(
    val date: String = "",
    val time: String = "",
    val reservationCount: Int = 1,
    val selectedSeats: PersistentList<SeatUiModel> = persistentListOf(),
    val totalPrice: Int = 0,
)
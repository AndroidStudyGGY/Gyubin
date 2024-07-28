package com.gyub.movieticket.movie.model

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/02
 */
data class SeatUiModel(
    val seat: Seat = Seat.A,
    val row: Int,
    val column: Int = START_COLUMN,
)

const val START_COLUMN = 1
const val END_COLUMN = 4

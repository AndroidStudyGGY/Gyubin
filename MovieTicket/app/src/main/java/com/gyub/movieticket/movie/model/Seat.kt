package com.gyub.movieticket.movie.model

import kotlinx.collections.immutable.persistentListOf

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/28
 */
enum class Seat(val grade: String, val price: Int) {
    B("B", 10_000),
    A("A", 12_000),
    S("C", 15_000);

    companion object {
        val seatRow = persistentListOf(
            Seat.B, Seat.B,  // 1, 2행: B등급
            Seat.S, Seat.S,  // 3, 4행: S등급
            Seat.A           // 5행: A등급
        )
    }
}
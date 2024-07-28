package com.gyub.movieticket.movie.model

import com.gyub.domain.model.MovieModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/27
 */
data class MovieUiModel(
    val code: String,
    val movieName: String,
    val genreName: String,
    val isAvailableScreening: Boolean,
    val price: Int,
)

fun MovieModel.toUiModel(): MovieUiModel {

    return MovieUiModel(
        code = code,
        movieName = movieName,
        genreName = genreName,
        isAvailableScreening = (Clock.System.todayIn(TimeZone.currentSystemDefault()).year in
                openDate.toInt()..endDate.toInt()),
        price = price
    )
}
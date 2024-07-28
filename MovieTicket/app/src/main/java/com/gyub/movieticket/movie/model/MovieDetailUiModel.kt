package com.gyub.movieticket.movie.model

import com.gyub.domain.model.MovieInfoModel

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */
data class MovieDetailUiModel(
    val code: String,
    val movieName: String,
    val genre: String,
)

fun MovieInfoModel.toMovieInfoUiModel() = MovieDetailUiModel(
    code = code,
    movieName = movieName,
    genre = genre.joinToString(" / ") { it.genreName },
//    price = price
)
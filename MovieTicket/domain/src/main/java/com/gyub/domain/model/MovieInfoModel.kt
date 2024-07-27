package com.gyub.domain.model

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */
data class MovieInfoModel(
    val code: String,
    val movieName: String,
    val genre: List<Genre>,
    val price: Int,
) {
    data class Genre(
        val genreName: String,
    )
}
package com.gyub.domain.model

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/25
 */
data class MovieModel(
    val code: String,
    val movieName: String,
    val openDate: String,
    val endDate: String,
    val genreName: String,
    val price: Int = 7000,
)
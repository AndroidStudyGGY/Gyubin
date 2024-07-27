package com.gyub.data.model

import com.gyub.data.network.model.MovieListResponse
import com.gyub.domain.model.MovieModel

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */

fun MovieListResponse.MovieItemResponse.toDomainModel() = MovieModel(
    code = code,
    movieName = movieName,
    genreName = genreName,
    price = 0
)
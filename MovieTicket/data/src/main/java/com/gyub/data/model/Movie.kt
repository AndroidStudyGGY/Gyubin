package com.gyub.data.model

import com.gyub.data.network.model.MovieListResponse
import com.gyub.domain.model.MovieModel

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */

fun MovieListResponse.MovieItemResponse.toDomainModel(): MovieModel {
    return MovieModel(
        code = code,
        movieName = movieName,
        genreName = genreName,
        openDate = if (openDate.length == 8) openDate.substring(0, 4) else "0",
        endDate = if (openDate.length == 8) openDate.substring(0, 4).toInt().plus(1).toString() else "0",
        price = 0
    )
}
package com.gyub.data.model

import com.gyub.data.network.model.MovieDetailResponse
import com.gyub.domain.model.MovieInfoModel

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */
fun MovieDetailResponse.toDomainModel(): MovieInfoModel {
    return MovieInfoModel(
        code = movieInfoResponse.movieItemInfoResponse.movieCode,
        movieName = movieInfoResponse.movieItemInfoResponse.movieName,
        genre = movieInfoResponse.movieItemInfoResponse.genreResponses.map {
            MovieInfoModel.Genre(it.genreName)
        },
        price = 7000
    )
}
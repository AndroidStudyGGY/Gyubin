package com.gyub.data.datasource.remote

import com.gyub.data.network.retrofit.MovieService
import javax.inject.Inject

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/25
 */
class MovieDataSource @Inject constructor(
    private val movieService: MovieService,
) {

    suspend fun getMovies(page: String = "1", size: String = "10") =
        movieService.getMovies(page, size)

    suspend fun getMovieDetail(code: String) =
        movieService.getMovieDetail(code)
}
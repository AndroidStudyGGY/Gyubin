package com.gyub.data.network.retrofit

import com.gyub.data.network.model.MovieDetailResponse
import com.gyub.data.network.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/25
 */
interface MovieService {

    @GET("movie/searchMovieList.json")
    suspend fun getMovies(
        @Query("curPage") page: String,
        @Query("itemPerPage") size: String,
    ): MovieResponse

    @GET("movie/searchMovieInfo.json")
    suspend fun getMovieDetail(
        @Query("movieCd") movieCode: String,
    ): MovieDetailResponse
}
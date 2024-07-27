package com.gyub.data.datasource

import com.gyub.data.datasource.remote.MovieDataSource
import com.gyub.data.network.FakeMovieService
import com.gyub.data.network.model.MovieDetailResponse
import com.gyub.data.network.model.MovieInfoResponse
import com.gyub.data.network.model.MovieItemInfoResponse
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/25
 */
class MovieItemResponseDataSourceTest {

    private val movieDataSource = MovieDataSource(FakeMovieService())

    @Test
    fun `영화 목록을 불러온다`() = runTest {
        assertEquals(
            movieDataSource.getMovies("1", "10")
                .movieListResponse
                .movieItemsResponse.size,
            10
        )
    }

    @Test
    fun `영화 정보를 불러온다`() = runTest {
        val movieDetailResponse = MovieDetailResponse(
            movieInfoResponse = MovieInfoResponse(
                MovieItemInfoResponse(
                    movieCode = "1234",
                    movieName = "Melva Talley",
                    movieNameEn = "Enrique Waller",
                    movieNameOg = null,
                    showTime = "adhuc",
                    productionYear = "persecuti",
                    openDate = "accumsan",
                    productionStatus = "tortor",
                    type = "veniam",
                    nationResponses = listOf(),
                    genreResponses = listOf(),
                    directorResponses = listOf(),
                    actorResponses = listOf(),
                    showTypeResponses = listOf(),
                    companies = listOf(),
                    auditResponses = listOf(),
                    staffResponses = listOf()
                ),
                source = ""
            )
        )

        assertEquals(
            expected = movieDetailResponse.movieInfoResponse
                .movieItemInfoResponse
                .movieCode,
            actual = movieDataSource.getMovieDetail("1234")
                .movieInfoResponse
                .movieItemInfoResponse
                .movieCode
        )
    }
}
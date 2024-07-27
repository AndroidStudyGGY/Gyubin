package com.gyub.data.datasource

import androidx.paging.PagingSource
import com.gyub.data.datasource.remote.MovieDataSource
import com.gyub.data.network.FakeMovieService
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/25
 */
class MovieItemResponsePagingSourceTest {

    private val fakeMovieService = FakeMovieService()
    private val movieDataSource = MovieDataSource(fakeMovieService)
    private val moviePagingSource = MoviesPagingSource(movieDataSource)

    @org.junit.Test
    fun `첫 번째 페이지를 반환한다`() = runTest {
        assertEquals(
            expected = PagingSource.LoadResult.Page(
                data = movieDataSource.getMovies("1", "10")
                    .movieListResponse
                    .movieItemsResponse,
                prevKey = null,
                nextKey = "2"
            ),
            actual = moviePagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = "1",
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )
        )
    }
}
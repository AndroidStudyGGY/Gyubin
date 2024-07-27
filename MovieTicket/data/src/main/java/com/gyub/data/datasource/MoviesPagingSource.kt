package com.gyub.data.datasource

import android.net.http.HttpException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gyub.data.datasource.remote.MovieDataSource
import com.gyub.data.network.model.MovieListResponse
import java.io.IOException
import javax.inject.Inject

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/25
 */
class MoviesPagingSource @Inject constructor(
    private val dataRepository: MovieDataSource,
) : PagingSource<String, MovieListResponse.MovieItemResponse>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, MovieListResponse.MovieItemResponse> {
        val page = params.key ?: "1"

        try {
            val response = dataRepository.getMovies(page, params.loadSize.toString()).movieListResponse

            val nextKey = if (response.totalCount == 0 || response.movieItemsResponse.isEmpty()) {
                null
            } else {
                page.toInt().plus(1).toString()
            }

            return LoadResult.Page(
                data = response.movieItemsResponse,
                prevKey = if (page == "1") null else page.toInt().minus(1).toString(),
                nextKey = nextKey
            )

        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, MovieListResponse.MovieItemResponse>): String? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)

            anchorPage?.prevKey?.toInt()?.plus(1)?.toString()
                ?: anchorPage?.nextKey?.toInt()?.minus(1)?.toString()
        }
    }
}
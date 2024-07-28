package com.gyub.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.gyub.data.datasource.MoviesPagingSource
import com.gyub.data.datasource.remote.MovieDataSource
import com.gyub.data.model.toDomainModel
import com.gyub.data.network.model.MovieListResponse
import com.gyub.domain.model.MovieInfoModel
import com.gyub.domain.model.MovieModel
import com.gyub.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/25
 */
@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val moviesPagingSource: MoviesPagingSource,
    private val movieDataSource: MovieDataSource,
) : MovieRepository {
    override fun getMovies(): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { moviesPagingSource }
        ).flow
            .map { pagingData ->
                pagingData.map(MovieListResponse.MovieItemResponse::toDomainModel)
            }
    }

    override suspend fun getMovieDetail(code: String): MovieInfoModel {
        return movieDataSource.getMovieDetail(code).toDomainModel()
    }
}
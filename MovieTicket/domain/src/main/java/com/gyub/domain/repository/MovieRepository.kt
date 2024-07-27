package com.gyub.domain.repository

import androidx.paging.PagingData
import com.gyub.domain.model.MovieInfoModel
import com.gyub.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/25
 */
interface MovieRepository {

    fun getMovies(): Flow<PagingData<MovieModel>>

    suspend fun getMovieDetail(code: String): MovieInfoModel
}
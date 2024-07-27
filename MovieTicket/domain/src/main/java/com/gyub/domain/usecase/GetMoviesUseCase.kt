package com.gyub.domain.usecase

import androidx.paging.PagingData
import com.gyub.domain.model.MovieModel
import com.gyub.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */
class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
) {

    operator fun invoke(): Flow<PagingData<MovieModel>> = repository.getMovies()
}
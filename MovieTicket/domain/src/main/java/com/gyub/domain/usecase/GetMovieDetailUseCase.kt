package com.gyub.domain.usecase

import com.gyub.domain.model.MovieInfoModel
import com.gyub.domain.repository.MovieRepository
import javax.inject.Inject

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */
class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository,
) {

    suspend operator fun invoke(code: String): MovieInfoModel =
        repository.getMovieDetail(code)
}
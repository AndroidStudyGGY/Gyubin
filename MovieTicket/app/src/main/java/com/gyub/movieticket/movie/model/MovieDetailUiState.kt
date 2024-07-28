package com.gyub.movieticket.movie.model

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */
sealed interface MovieDetailUiState {
    data object Loading : MovieDetailUiState
    data class Success(
        val movieInfo: MovieDetailUiModel,
    ) : MovieDetailUiState
}
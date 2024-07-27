package com.gyub.movieticket.movie.model

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/27
 */
sealed interface ReservationResultUiState {

    data object Idle : ReservationResultUiState

    data object Loading : ReservationResultUiState

    data object Error : ReservationResultUiState

    data class Success(
        val result: ReservationResultInfoUiModel,
    ) : ReservationResultUiState
}
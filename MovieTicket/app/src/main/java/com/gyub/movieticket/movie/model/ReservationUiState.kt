package com.gyub.movieticket.movie.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/27
 */
@Stable
sealed interface ReservationUiState {

    @Immutable
    data object Idle : ReservationUiState

    @Immutable
    data object Loading : ReservationUiState

    @Immutable
    data object Error : ReservationUiState

    @Immutable
    data class Success(
        val result: ReservationResultInfoUiModel,
    ) : ReservationUiState
}
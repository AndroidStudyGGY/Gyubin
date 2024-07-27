package com.gyub.movieticket.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gyub.domain.usecase.GetMovieDetailUseCase
import com.gyub.movieticket.movie.model.MovieDetailUiState
import com.gyub.movieticket.movie.model.ReservationInfoUiState
import com.gyub.movieticket.movie.model.ReservationResultInfoUiModel
import com.gyub.movieticket.movie.model.ReservationResultUiState
import com.gyub.movieticket.movie.model.toMovieInfoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
) : ViewModel() {
    private val _movieInfoUiState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val movieInfoUiState = _movieInfoUiState.asStateFlow()

    private val _reservationInfoUiState = MutableStateFlow(ReservationInfoUiState())
    val reservationInfoUiState = _reservationInfoUiState.asStateFlow()

    private val _reservationResultUiState = MutableStateFlow<ReservationResultUiState>(ReservationResultUiState.Idle)
    val reservationResultUiState = _reservationResultUiState.asStateFlow()

    fun fetchMovieInfo(movieCode: String) {
        viewModelScope.launch {
            val movieInfo = getMovieDetailUseCase(movieCode)

            _movieInfoUiState.value = MovieDetailUiState.Success(
                movieInfo.toMovieInfoUiModel(),
                0
            )
        }
    }

    fun increaseReservationCount() {
        _reservationInfoUiState.update { state ->
            val newCount = state.reservationCount + 1
            state.copy(reservationCount = newCount, totalPrice = newCount * getCurrentMoviePrice())
        }
    }

    fun decreaseReservationCount() {
        _reservationInfoUiState.update { state ->
            val newCount = state.reservationCount - 1
            state.copy(reservationCount = newCount, totalPrice = newCount * getCurrentMoviePrice())
        }
    }

    // 예상은 예약 api 호출하면 응답으로 예약 결과를 받아오는 것
    fun reservation() {
        viewModelScope.launch {
            _reservationResultUiState.value = ReservationResultUiState.Loading

            delay(300L)

            val movieInfoState = movieInfoUiState.value
            if (movieInfoState !is MovieDetailUiState.Success) {
                _reservationResultUiState.value = ReservationResultUiState.Error
                return@launch
            }

            _reservationResultUiState.value = ReservationResultUiState.Success(
                ReservationResultInfoUiModel(
                    movieName = movieInfoState.movieInfo.movieName,
                    movieGenre = movieInfoState.movieInfo.genre,
                    reservedCount = reservationInfoUiState.value.reservationCount,
                    totalPrice = reservationInfoUiState.value.totalPrice
                )
            )
        }
    }

    fun resetReservationResult() {
        _reservationResultUiState.value = ReservationResultUiState.Idle
    }

    private fun getCurrentMoviePrice(): Int {
        val currentState = _movieInfoUiState.value
        if (currentState !is MovieDetailUiState.Success) {
            return 0
        }

        return currentState.movieInfo.price
    }
}
package com.gyub.movieticket.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gyub.domain.usecase.GetMovieDetailUseCase
import com.gyub.movieticket.movie.model.MovieDetailUiState
import com.gyub.movieticket.movie.model.ReservationDetailUiModel
import com.gyub.movieticket.movie.model.ReservationResultInfoUiModel
import com.gyub.movieticket.movie.model.ReservationUiState
import com.gyub.movieticket.movie.model.SeatUiModel
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

    private val _reservationDetailUiModel = MutableStateFlow(ReservationDetailUiModel())
    val reservationInfoUiModel = _reservationDetailUiModel.asStateFlow()

    private val _reservationUiState = MutableStateFlow<ReservationUiState>(ReservationUiState.Idle)
    val reservationResultUiState = _reservationUiState.asStateFlow()

    fun fetchMovieInfo(movieCode: String) {
        viewModelScope.launch {
            val movieInfo = getMovieDetailUseCase(movieCode)

            _movieInfoUiState.value = MovieDetailUiState.Success(
                movieInfo.toMovieInfoUiModel(),
            )
        }
    }

    fun increaseReservationCount() {
        _reservationDetailUiModel.update { state ->
            val currentCount = state.reservationCount

            val newCount = if (currentCount == 100) currentCount else currentCount + 1
            state.copy(reservationCount = newCount)
        }
    }

    fun decreaseReservationCount() {
        _reservationDetailUiModel.update { state ->
            val currentCount = state.reservationCount

            val newCount = if (currentCount == 1) currentCount else currentCount - 1
            state.copy(reservationCount = newCount)
        }
    }

    fun onSeatClick(seat: SeatUiModel) {
        _reservationDetailUiModel.update { state ->
            state.copy(
                selectedSeats = if (state.selectedSeats.contains(seat)) {
                    state.selectedSeats.remove(seat)
                } else {
                    if (state.selectedSeats.size < state.reservationCount) {
                        state.selectedSeats.add(seat)
                    } else {
                        state.selectedSeats
                    }
                }

            )
        }

        calcTotalPrice()
    }

    fun setReservationDate(date: String, time: String) {
        _reservationDetailUiModel.update { state ->
            state.copy(
                date = date,
                time = time
            )
        }
        calcTotalPrice()
    }

    // 예상은 예약 api 호출하면 응답으로 예약 결과를 받아오는 것
    fun reservation() {
        viewModelScope.launch {
            _reservationUiState.value = ReservationUiState.Loading

            delay(300L)

            val movieInfoState = movieInfoUiState.value
            if (movieInfoState !is MovieDetailUiState.Success) {
                _reservationUiState.value = ReservationUiState.Error
                return@launch
            }

            _reservationUiState.value = ReservationUiState.Success(
                ReservationResultInfoUiModel(
                    movieName = movieInfoState.movieInfo.movieName,
                    movieGenre = movieInfoState.movieInfo.genre,
                    reservedCount = reservationInfoUiModel.value.reservationCount,
                    totalPrice = reservationInfoUiModel.value.totalPrice
                )
            )
        }
    }

    private fun calcTotalPrice() {
        _reservationDetailUiModel.update { state ->
            val basePrice = state.selectedSeats.sumOf { it.seat.price }

            val isMovieDay = isMovieDay(state.date)
            val isEarlyOrLate = isEarlyOrLate(state.time)

            val discountedPrice = if (isMovieDay) {
                basePrice * 0.9
            } else {
                basePrice.toDouble()
            }

            val finalPrice = if (isEarlyOrLate) {
                (discountedPrice - 2000 * state.selectedSeats.size).coerceAtLeast(0.0)
            } else {
                discountedPrice
            }

            state.copy(totalPrice = finalPrice.toInt())
        }
    }

    private fun isMovieDay(date: String): Boolean {
        val day = date.split(" ").getOrNull(2)?.removeSuffix("일")?.toIntOrNull() ?: 0
        return day == 10 || day == 20 || day == 30
    }

    private fun isEarlyOrLate(time: String): Boolean {
        val hour = time.split(" ").getOrNull(0)?.removeSuffix("시")?.toIntOrNull() ?: 0
        return hour < 11 || hour >= 20
    }

    fun resetReservationResult() {
        _reservationUiState.value = ReservationUiState.Idle
    }
}
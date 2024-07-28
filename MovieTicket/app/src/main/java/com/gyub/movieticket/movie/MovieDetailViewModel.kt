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
            val currentCount = state.reservationCount

            val newCount = if (currentCount == 100) currentCount else currentCount + 1
            state.copy(reservationCount = newCount, totalPrice = newCount * getCurrentMoviePrice(state.date))
        }
    }

    fun decreaseReservationCount() {
        _reservationInfoUiState.update { state ->
            val currentCount = state.reservationCount

            val newCount = if (currentCount == 1) currentCount else currentCount - 1
            state.copy(reservationCount = newCount, totalPrice = newCount * getCurrentMoviePrice(state.date))
        }
    }

    fun setReservationDate(date: String) {
        _reservationInfoUiState.update { state ->
            state.copy(
                date = date,
                totalPrice = state.reservationCount * getCurrentMoviePrice(date)
            )
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
    private fun getCurrentMoviePrice(date: String): Int {
        val currentState = _movieInfoUiState.value
        if (currentState !is MovieDetailUiState.Success) {
            return 0
        }

        var price = currentState.movieInfo.price

        // 날짜와 시간을 추출
        val dateTimePattern = Regex("""(\d{4})년 (\d{1,2})월 (\d{1,2})일 (\d{1,2})시 (\d{1,2})분""")
        val matchResult = dateTimePattern.matchEntire(date)
        val (year, month, day, hour, minute) = matchResult?.destructured ?: return price

        val dayOfMonth = day.toInt()
        val selectedHour = hour.toInt()

        // 무비데이 할인
        if (dayOfMonth == 10 || dayOfMonth == 20 || dayOfMonth == 30) {
            price = (price * 0.9).toInt()
        }

        // 조조/야간 할인
        if (selectedHour < 11 || selectedHour >= 20) {
            price -= 2000
        }

        return price
    }
}
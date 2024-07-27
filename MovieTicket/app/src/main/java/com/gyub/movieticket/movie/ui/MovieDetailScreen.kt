package com.gyub.movieticket.movie.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gyub.movieticket.movie.MovieDetailViewModel
import com.gyub.movieticket.movie.model.MovieDetailUiModel
import com.gyub.movieticket.movie.model.MovieDetailUiState
import com.gyub.movieticket.movie.model.ReservationInfoUiState
import com.gyub.movieticket.movie.model.ReservationResultInfoUiModel
import com.gyub.movieticket.movie.model.ReservationResultUiState
import com.gyub.movieticket.ui.component.LoadingIndicator
import kotlin.reflect.KFunction0

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */
@Composable
fun MovieDetailRoute(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
    movieCode: String,
    moveReservationResult: (ReservationResultInfoUiModel) -> Unit,
) {
    val movieInfoUiState by viewModel.movieInfoUiState.collectAsStateWithLifecycle()
    val reservationInfo by viewModel.reservationInfoUiState.collectAsStateWithLifecycle()
    val reservationResultUiState by viewModel.reservationResultUiState.collectAsStateWithLifecycle()

    MovieDetailContent(
        innerPadding = innerPadding,
        movieInfo = movieInfoUiState,
        reservationInfo = reservationInfo,
        increaseReservationCount = viewModel::increaseReservationCount,
        decreaseReservationCount = viewModel::decreaseReservationCount,
        reservation = viewModel::reservation,
    )

    LaunchedEffect(movieCode) {
        viewModel.fetchMovieInfo(movieCode)
    }
    LaunchedEffect(reservationResultUiState) {
        when (val reservationResult = reservationResultUiState) {
            ReservationResultUiState.Error -> {
                /** showErrorToast */
            }

            ReservationResultUiState.Idle -> {}
            ReservationResultUiState.Loading -> {
                /** LoadingIndicator() */
            }

            is ReservationResultUiState.Success -> {
                moveReservationResult(reservationResult.result)
                viewModel.resetReservationResult()
            }
        }
    }
}

@Composable
fun MovieDetailContent(
    innerPadding: PaddingValues,
    movieInfo: MovieDetailUiState,
    reservationInfo: ReservationInfoUiState,
    increaseReservationCount: () -> Unit,
    decreaseReservationCount: () -> Unit,
    reservation: KFunction0<Unit>,
) {
    when (movieInfo) {
        is MovieDetailUiState.Loading -> LoadingIndicator(Modifier.fillMaxSize())
        is MovieDetailUiState.Success -> MovieDetailScreen(
            innerPadding = innerPadding,
            movieInfo = movieInfo.movieInfo,
            reservationCount = reservationInfo.reservationCount,
            totalPrice = reservationInfo.totalPrice,
            increaseReservationCount = increaseReservationCount,
            decreaseReservationCount = decreaseReservationCount,
            reservation = reservation
        )
    }
}

@Composable
fun MovieDetailScreen(
    innerPadding: PaddingValues,
    movieInfo: MovieDetailUiModel,
    reservationCount: Int,
    totalPrice: Int,
    increaseReservationCount: () -> Unit,
    decreaseReservationCount: () -> Unit,
    reservation: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MovieInfo(
            modifier = Modifier.align(Alignment.Center),
            movieInfo = movieInfo
        )
        Reservation(
            modifier = Modifier.align(Alignment.BottomCenter),
            reservationCount = reservationCount,
            totalPrice = totalPrice,
            increaseReservationCount = increaseReservationCount,
            decreaseReservationCount = decreaseReservationCount,
            reservation = reservation,
        )
        // 뒤로가기
    }
}

@Composable
fun MovieInfo(
    modifier: Modifier = Modifier,
    movieInfo: MovieDetailUiModel,
) {
    Column(modifier) {
        Text(
            text = movieInfo.code,
            fontSize = 10.sp
        )
        Text(
            text = movieInfo.movieName,
            fontSize = 20.sp
        )
        Text(
            text = movieInfo.genre,
            fontSize = 15.sp
        )
    }
}

@Composable
fun Reservation(
    modifier: Modifier = Modifier,
    reservationCount: Int,
    totalPrice: Int,
    increaseReservationCount: () -> Unit,
    decreaseReservationCount: () -> Unit,
    reservation: () -> Unit,
) {
    Column(modifier) {
        Row {
            IconButton(
                onClick = increaseReservationCount,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "")
            }

            Text(
                text = "예약 인원: ${reservationCount}명",
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            IconButton(
                onClick = decreaseReservationCount,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "")
            }
        }

        Text(text = "총 예약금액: $totalPrice")

        Button(onClick = { reservation() }) {
            Text(text = "예약하기")
        }
    }
}

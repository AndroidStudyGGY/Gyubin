package com.gyub.movieticket.movie.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gyub.movieticket.movie.MovieDetailViewModel
import com.gyub.movieticket.movie.component.Calendar
import com.gyub.movieticket.movie.component.ReservationConfirmationDialog
import com.gyub.movieticket.movie.component.SeatSelection
import com.gyub.movieticket.movie.model.MovieDetailUiModel
import com.gyub.movieticket.movie.model.MovieDetailUiState
import com.gyub.movieticket.movie.model.ReservationDetailUiModel
import com.gyub.movieticket.movie.model.ReservationResultInfoUiModel
import com.gyub.movieticket.movie.model.ReservationUiState
import com.gyub.movieticket.movie.model.SeatUiModel
import com.gyub.movieticket.ui.component.LoadingIndicator
import kotlinx.collections.immutable.PersistentList

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
    val reservationInfo by viewModel.reservationInfoUiModel.collectAsStateWithLifecycle()
    val reservationResultUiState by viewModel.reservationResultUiState.collectAsStateWithLifecycle()

    MovieDetailContent(
        innerPadding = innerPadding,
        movieInfo = movieInfoUiState,
        reservationInfo = reservationInfo,
        increaseReservationCount = viewModel::increaseReservationCount,
        decreaseReservationCount = viewModel::decreaseReservationCount,
        setReservationDate = viewModel::setReservationDate,
        onSeatClick = viewModel::onSeatClick,
        reservation = viewModel::reservation,
    )

    LaunchedEffect(movieCode) {
        viewModel.fetchMovieInfo(movieCode)
    }
    LaunchedEffect(reservationResultUiState) {
        when (val reservationResult = reservationResultUiState) {
            ReservationUiState.Error -> {
                /** showErrorToast */
            }

            ReservationUiState.Idle -> {}
            ReservationUiState.Loading -> {
                /** LoadingIndicator() */
            }

            is ReservationUiState.Success -> {
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
    reservationInfo: ReservationDetailUiModel,
    increaseReservationCount: () -> Unit,
    decreaseReservationCount: () -> Unit,
    onSeatClick: (SeatUiModel) -> Unit,
    setReservationDate: (String, String) -> Unit,
    reservation: () -> Unit,
) {
    when (movieInfo) {
        is MovieDetailUiState.Loading -> LoadingIndicator(Modifier.fillMaxSize())
        is MovieDetailUiState.Success -> MovieDetailScreen(
            innerPadding = innerPadding,
            movieInfo = movieInfo.movieInfo,
            reservationCount = reservationInfo.reservationCount,
            selectedSeats = reservationInfo.selectedSeats,
            totalPrice = reservationInfo.totalPrice,
            increaseReservationCount = increaseReservationCount,
            decreaseReservationCount = decreaseReservationCount,
            onSeatClick = onSeatClick,
            setReservationDate = setReservationDate,
            reservation = reservation
        )
    }
}

@Composable
fun MovieDetailScreen(
    innerPadding: PaddingValues,
    movieInfo: MovieDetailUiModel,
    reservationCount: Int,
    selectedSeats: PersistentList<SeatUiModel>,
    totalPrice: Int,
    increaseReservationCount: () -> Unit,
    decreaseReservationCount: () -> Unit,
    onSeatClick: (SeatUiModel) -> Unit,
    setReservationDate: (String, String) -> Unit,
    reservation: () -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MovieInfo(
            modifier = Modifier.weight(0.5f),
            movieInfo = movieInfo
        )
        Calendar(
            modifier = Modifier.weight(2f),
            setReservationDate = setReservationDate,
        )
        ReservationCount(
            modifier = Modifier.weight(0.5f),
            reservationCount = reservationCount,
            increaseReservationCount = increaseReservationCount,
            decreaseReservationCount = decreaseReservationCount
        )
        SeatSelection(
            modifier = Modifier.weight(2f),
            selectedSeats = selectedSeats,
            onSeatClick = onSeatClick,
        )

        Reservation(
            modifier = Modifier.weight(0.5f),
            reservationCount = reservationCount,
            selectedSeats = selectedSeats,
            totalPrice = totalPrice,
            showDialog = { showDialog = true }
        )

        ReservationConfirmationDialog(
            showDialog = showDialog,
            onDismissRequest = { showDialog = false },
            onConfirm = reservation,
            selectedSeats = selectedSeats,
            totalPrice = totalPrice
        )

    }
}

@Composable
fun MovieInfo(
    modifier: Modifier = Modifier,
    movieInfo: MovieDetailUiModel,
) {
    Column(modifier) {
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
fun ReservationCount(
    modifier: Modifier = Modifier,
    reservationCount: Int,
    increaseReservationCount: () -> Unit,
    decreaseReservationCount: () -> Unit,
) {
    Row(modifier = modifier) {
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
}

@Composable
fun Reservation(
    modifier: Modifier = Modifier,
    totalPrice: Int,
    reservationCount: Int,
    selectedSeats: PersistentList<SeatUiModel>,
    showDialog: () -> Unit,
) {
    Column(modifier) {
        Text(text = "총 예약금액: $totalPrice")

        Button(
            enabled = reservationCount == selectedSeats.size,
            onClick = { showDialog() }
        ) {
            Text(text = "예약하기")
        }
    }
}

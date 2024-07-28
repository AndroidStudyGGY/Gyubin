package com.gyub.movieticket.movie.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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
        setReservationDate = viewModel::setReservationDate,
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
    reservation: () -> Unit,
    setReservationDate: (String) -> Unit,
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
    totalPrice: Int,
    increaseReservationCount: () -> Unit,
    decreaseReservationCount: () -> Unit,
    reservation: () -> Unit,
    setReservationDate: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MovieInfo(
            modifier = Modifier.align(Alignment.TopCenter),
            movieInfo = movieInfo
        )
        Calender(
            modifier = Modifier.align(Alignment.Center),
            setReservationDate = setReservationDate,
        )
        Reservation(
            modifier = Modifier.align(Alignment.BottomCenter),
            reservationCount = reservationCount,
            totalPrice = totalPrice,
            increaseReservationCount = increaseReservationCount,
            decreaseReservationCount = decreaseReservationCount,
            reservation = reservation,
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
fun Calender(
    modifier: Modifier = Modifier,
    setReservationDate: (String) -> Unit,
) {
    val context = LocalContext.current

    val currentDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time

    var selectedDate by remember { mutableStateOf(currentDate) }
    var selectedTime by remember { mutableStateOf(java.time.LocalTime.of(currentTime.hour, currentTime.minute).plusHours(1)) }

    val isWeekend = selectedDate.dayOfWeek == DayOfWeek.SATURDAY || selectedDate.dayOfWeek == DayOfWeek.SUNDAY

    val availableTimes = remember(selectedDate) {
        val startHour = if (isWeekend) 9 else 10
        val endHour = 24
        (startHour until endHour step 2).filter { it > currentTime.hour || selectedDate > currentDate }.toList()
    }

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("날짜 선택", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            showDatePicker(context) { date ->
                selectedDate = date
            }
        }) {
            Text(text = selectedDate.toString())
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("시간 선택", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { expanded = true }) {
            Text(text = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm")))
        }

        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            availableTimes.forEach { time ->
                DropdownMenuItem(
                    onClick = {
                        selectedTime = LocalTime.of(time, 0)
                        expanded = false
                    },
                    text = { Text("$time:00") }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val formattedDate = "${selectedDate.year}년 ${selectedDate.monthNumber}월 ${selectedDate.dayOfMonth}일 ${selectedTime.hour}시 ${selectedTime.minute}분"
            setReservationDate(formattedDate)
        }) {
            Text(text = "날짜 선택 완료")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

fun showDatePicker(context: android.content.Context, onDateSelected: (kotlinx.datetime.LocalDate) -> Unit) {
    val datePickerDialog = android.app.DatePickerDialog(context)
    datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
        onDateSelected(kotlinx.datetime.LocalDate(year, month + 1, dayOfMonth))
    }
    datePickerDialog.show()
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

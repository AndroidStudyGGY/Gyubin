package com.gyub.movieticket.movie.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/02
 */
@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    setReservationDate: (String, String) -> Unit,
) {
    val currentDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
    var selectedDate by remember { mutableStateOf(currentDate) }
    var selectedTime by remember { mutableStateOf(LocalTime.of(currentTime.hour, currentTime.minute)) }
    var expanded by remember { mutableStateOf(false) }
    val availableTimes = calculateAvailableTimes(selectedDate)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DateSelector(selectedDate) { selectedDate = it }
        Spacer(modifier = Modifier.height(16.dp))
        TimeSelector(
            selectedTime = selectedTime,
            availableTimes = availableTimes,
            expanded = expanded,
            onExpandedChange = { expanded = it },
            onTimeSelected = { selectedTime = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ConfirmButton(selectedDate, selectedTime, setReservationDate)
    }
}

@Composable
private fun DateSelector(selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val context = LocalContext.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("날짜 선택", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showDatePicker(context, onDateSelected) }) {
            Text(text = selectedDate.toString())
        }
    }
}

@Composable
private fun TimeSelector(
    selectedTime: LocalTime,
    availableTimes: List<Int>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onTimeSelected: (LocalTime) -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("시간 선택", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onExpandedChange(true) }) {
            Text(text = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm")))
        }
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            availableTimes.forEach { time ->
                DropdownMenuItem(
                    onClick = {
                        onTimeSelected(LocalTime.of(time, 0))
                        onExpandedChange(false)
                    },
                    text = { Text("$time:00") }
                )
            }
        }
    }
}

@Composable
private fun ConfirmButton(
    selectedDate: LocalDate,
    selectedTime: LocalTime,
    setReservationDate: (String, String) -> Unit,
) {
    Button(onClick = {
        setReservationDate(
            "${selectedDate.year}년 ${selectedDate.monthNumber}월 ${selectedDate.dayOfMonth}일",
            "${selectedTime.hour}시 ${selectedTime.minute}분"
        )
    }) {
        Text(text = "날짜 선택 완료")
    }
}

private fun calculateAvailableTimes(selectedDate: LocalDate): List<Int> {
    val currentDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
    val isWeekend = selectedDate.dayOfWeek == DayOfWeek.SATURDAY || selectedDate.dayOfWeek == DayOfWeek.SUNDAY

    val startHour = if (isWeekend) WEEKEND_START_HOUR else WEEKDAY_START_HOUR
    return (startHour until END_HOUR step 2)
        .filter { it > currentTime.hour || selectedDate > currentDate }
}

fun showDatePicker(context: android.content.Context, onDateSelected: (LocalDate) -> Unit) {
    val datePickerDialog = android.app.DatePickerDialog(context)
    datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
        onDateSelected(LocalDate(year, month + 1, dayOfMonth))
    }
    datePickerDialog.show()
}

@Composable
@Preview
fun CalendarPreview() {
    Calendar(setReservationDate = { _, _ -> })
}
private const val WEEKEND_START_HOUR = 9
private const val WEEKDAY_START_HOUR = 10
private const val END_HOUR = 24
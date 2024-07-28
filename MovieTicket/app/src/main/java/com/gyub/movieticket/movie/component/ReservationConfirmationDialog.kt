package com.gyub.movieticket.movie.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import com.gyub.movieticket.movie.model.SeatUiModel
import kotlinx.collections.immutable.PersistentList

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/28
 */
@Composable
fun ReservationConfirmationDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    selectedSeats: PersistentList<SeatUiModel>,
    totalPrice: Int,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "예매 확인") },
            text = {
                Column {
                    Text(text = "선택된 좌석: ${selectedSeats.joinToString { "${it.seat.grade}${it.column}" }}")
                    Text(text = "총 가격: $totalPrice 원")
                }
            },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text("확인")
                }
            },
            dismissButton = {
                Button(onClick = onDismissRequest) {
                    Text("취소")
                }
            },
            properties = DialogProperties(dismissOnClickOutside = false)
        )
    }
}
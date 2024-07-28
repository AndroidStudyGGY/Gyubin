package com.gyub.movieticket.movie.componen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gyub.movieticket.movie.model.Seat
import com.gyub.movieticket.movie.model.SeatUiModel
import com.gyub.movieticket.ui.theme.Purple40
import kotlinx.collections.immutable.PersistentList

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/28
 */
@Composable
fun TableLayout(
    rows: PersistentList<Seat>,
    startColumn: Int,
    endColumn: Int,
    selectedSeats: PersistentList<SeatUiModel>,
    onSeatClick: (SeatUiModel) -> Unit,
) {
    Column {
        rows.forEachIndexed { rowIndex, seatGrade ->
            Row {
                (startColumn..endColumn).forEach { col ->
                    val seatUiModel = SeatUiModel(seatGrade, rowIndex + 1, col)
                    val isSelected = selectedSeats.contains(seatUiModel)

                    val seatColor = when (seatGrade) {
                        Seat.B -> Purple40
                        Seat.S -> Color.Green
                        Seat.A -> Color.Black
                    }
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = if (isSelected) Color.LightGray else Color.Transparent,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .clickable { onSeatClick(seatUiModel) }
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${rowIndex + 1}$col${seatUiModel.seat.grade}",
                            color = seatColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
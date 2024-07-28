package com.gyub.movieticket.movie.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gyub.movieticket.movie.componen.TableLayout
import com.gyub.movieticket.movie.model.END_COLUMN
import com.gyub.movieticket.movie.model.START_COLUMN
import com.gyub.movieticket.movie.model.Seat
import com.gyub.movieticket.movie.model.SeatUiModel
import kotlinx.collections.immutable.PersistentList

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/28
 */
@Composable
fun SeatSelection(
    modifier: Modifier = Modifier,
    selectedSeats: PersistentList<SeatUiModel>,
    onSeatClick: (SeatUiModel) -> Unit,
) {
    val rows = Seat.seatRow
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TableLayout(
            rows = rows,
            startColumn = START_COLUMN,
            endColumn = END_COLUMN,
            selectedSeats = selectedSeats,
            onSeatClick = onSeatClick
        )
    }
}
package com.gyub.movieticket.movie.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.gyub.movieticket.movie.model.ReservationResultInfoUiModel

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/27
 */
@Composable
fun ReservationResultRoute(
    innerPadding: PaddingValues,
    reservedInfo: ReservationResultInfoUiModel,
) {
    ReservationResultContent(
        innerPadding = innerPadding,
        reservedInfo = reservedInfo
    )
}

@Composable
fun ReservationResultContent(
    innerPadding: PaddingValues,
    reservedInfo: ReservationResultInfoUiModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = reservedInfo.movieName,
            fontSize = 20.sp
        )
        Text(
            text = reservedInfo.movieGenre,
            fontSize = 15.sp
        )
        Text(
            text = "예약 인원: ${reservedInfo.reservedCount}명",
            fontSize = 13.sp
        )
        Text(
            text = "결제 금액: ${reservedInfo.totalPrice}원",
            fontSize = 13.sp
        )
    }
}

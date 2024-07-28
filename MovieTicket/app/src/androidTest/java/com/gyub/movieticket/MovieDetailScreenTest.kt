package com.gyub.movieticket

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.gyub.movieticket.movie.model.MovieDetailUiModel
import com.gyub.movieticket.movie.model.Seat
import com.gyub.movieticket.movie.model.SeatUiModel
import com.gyub.movieticket.movie.ui.MovieDetailScreen
import com.gyub.movieticket.movie.ui.Reservation
import com.gyub.movieticket.movie.ui.ReservationCount
import kotlinx.collections.immutable.persistentListOf
import org.junit.Rule
import org.junit.Test

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/04
 */
class MovieDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun movieInfoDisplayedCorrectly() {
        val movieInfo = MovieDetailUiModel(
            code = "testCode",
            movieName = "테스트 영화",
            genre = "액션"
        )

        composeTestRule.setContent {
            MovieDetailScreen(
                innerPadding = PaddingValues(),
                movieInfo = movieInfo,
                reservationCount = 1,
                selectedSeats = persistentListOf(),
                totalPrice = 10000,
                increaseReservationCount = {},
                decreaseReservationCount = {},
                onSeatClick = {},
                setReservationDate = { _, _ -> },
                reservation = {}
            )
        }

        composeTestRule.onNodeWithText("테스트 영화").assertIsDisplayed()
        composeTestRule.onNodeWithText("액션").assertIsDisplayed()
    }

    @Test
    fun reservationCountUpdatesCorrectly() {
        composeTestRule.setContent {
            ReservationCount(
                reservationCount = 2,
                increaseReservationCount = {},
                decreaseReservationCount = {}
            )
        }

        composeTestRule.onNodeWithText("예약 인원: 2명").assertIsDisplayed()
    }

    @Test
    fun reservationButtonEnabledWhenSeatsSelected() {
        composeTestRule.setContent {
            Reservation(
                totalPrice = 20000,
                reservationCount = 2,
                selectedSeats = persistentListOf(
                    SeatUiModel(Seat.A, 1, 1),
                    SeatUiModel(Seat.A, 2, 1)
                ),
                showDialog = {}
            )
        }

        composeTestRule.onNodeWithText("총 예약금액: 20000").assertIsDisplayed()
        composeTestRule.onNodeWithText("예약하기").assertIsEnabled()
    }

    @Test
    fun reservationButtonDisabledWhenSeatsNotMatched() {
        composeTestRule.setContent {
            Reservation(
                totalPrice = 10000,
                reservationCount = 2,
                selectedSeats = persistentListOf(SeatUiModel(Seat.A, 1, 1)),
                showDialog = {}
            )
        }

        composeTestRule.onNodeWithText("예약하기").assertIsNotEnabled()
    }
}
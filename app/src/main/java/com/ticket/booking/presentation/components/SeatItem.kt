package com.ticket.booking.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import com.ticket.booking.R
import com.ticket.booking.domain.model.Seat
import com.ticket.booking.domain.model.SeatCategory
import kotlin.math.abs

@Composable
fun SeatItem(
    modifier: Modifier = Modifier,
    seat: Seat,
    onSeatClick: () -> Unit
) {
    val seatCategory = SeatCategory.fromString(seat.seatType)

    val seatIcon = when (seatCategory) {
        SeatCategory.VIP -> R.drawable.seat_1
        SeatCategory.COMFORT -> R.drawable.seat_2
        SeatCategory.STANDARD -> R.drawable.seat_3
        SeatCategory.SELECTED -> R.drawable.seat_selected
        else -> R.drawable.seat_booked
    }

    Log.d("SEAT_DEBUG", "Seat type: ${seat.seatType}")
    Log.d("SEAT_DEBUG", "Seat icon: $seatIcon")

    val seatNumber = when (seat.seatType) {
        null -> "X"
        SeatCategory.SELECTED.toString() -> seat.bookedSeats.toString()
        else -> ""
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.pointerInput(Unit) {
            var startPosition = Offset.Zero
            var isSingleTouch = true

            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    when {
                        event.changes.any { it.pressed } -> {
                            isSingleTouch = event.changes.size == 1
                            if (isSingleTouch) {
                                startPosition = event.changes.first().position
                            }
                        }
                        event.changes.any { !it.pressed } -> {
                            if (isSingleTouch) {
                                val endPosition = event.changes.first().position
                                val distance = abs(endPosition.x - startPosition.x) + 
                                             abs(endPosition.y - startPosition.y)
                                if (distance < 10f && seat.seatType != null) {
                                    onSeatClick()
                                }
                            }
                            isSingleTouch = true
                            startPosition = Offset.Zero
                        }
                    }
                }
            }
        }
    ) {
        Image(
            painter = painterResource(seatIcon),
            contentDescription = "Seat ${seat.objectDescription}"
        )

        Text(text = seatNumber)
    }
}
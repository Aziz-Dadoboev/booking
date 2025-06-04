package com.ticket.booking.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import com.ticket.booking.R
import com.ticket.booking.data.model.Seat
import kotlin.math.abs

@Composable
fun SeatItem(
    modifier: Modifier = Modifier,
    seat: Seat,
    onSeatClick: () -> Unit
) {
    val seatCategory = seat.seat_type

    val seatIcon = when (seatCategory) {
        "VIP" -> R.drawable.seat_1
        "COMFORT" -> R.drawable.seat_2
        "STANDARD" -> R.drawable.seat_3
        else -> R.drawable.seat_booked
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
                                if (distance < 10f) {
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
            contentDescription = "Seat ${seat.object_description}"
        )
//        Text(text = seatNumber)
    }
}
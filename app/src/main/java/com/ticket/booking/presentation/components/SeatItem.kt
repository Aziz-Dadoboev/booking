package com.ticket.booking.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.ticket.booking.R
import com.ticket.booking.data.model.Seat

@Composable
fun SeatItem(
    modifier: Modifier = Modifier,
    seat: Seat,
    isSelected: Boolean,
    selectedNum: Int,
) {
    val seatCategory = seat.seat_type

    var seatIcon = when (seatCategory) {
        "VIP" -> R.drawable.seat_1
        "COMFORT" -> R.drawable.seat_2
        "STANDARD" -> R.drawable.seat_3
        else -> R.drawable.seat_booked
    }
    if (isSelected) {
        seatIcon = R.drawable.seat_selected
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(seatIcon),
            contentDescription = "Seat ${seat.object_description}"
        )
        if (isSelected && selectedNum > 0) {
            Text(text = "$selectedNum")
        }
    }
}
package com.ticket.booking.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ticket.booking.data.model.HallSchemeModel
import com.ticket.booking.data.model.Seat
import com.ticket.booking.data.model.SeatsType
import com.ticket.booking.presentation.screens.hall.HallViewModel

@Composable
fun HallScreenContent(
    modifier: Modifier = Modifier,
    hallSchemeModel: HallSchemeModel,
    viewModel: HallViewModel
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column {
            HallHeader(
                hallName = hallSchemeModel.hall_name,
                sessionDateTime = "${hallSchemeModel.session_time}, ${hallSchemeModel.session_time}"
            )

            HallMap(
                modifier = Modifier.weight(1f),
                seats = hallSchemeModel.seats,
                mapWidth = hallSchemeModel.map_width,
                mapHeight = hallSchemeModel.map_height
            )
        }

        AnimatedVisibility(
            visible = false,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            HallFooter(
                selectedSeatsCount = 0,
                totalAmount = calculateTotalAmount(emptyList(), hallSchemeModel.seats_type),
                onPayClick = viewModel::onPayClick
            )
        }
    }
}

private fun calculateTotalAmount(seats: List<Seat>, seatsType: List<SeatsType>): Int {
    return seats.sumOf { seat ->
        seatsType.find { it.seat_type == seat.seat_type }?.price ?: 0
    }
}
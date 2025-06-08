package com.ticket.booking.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ticket.booking.R
import com.ticket.booking.data.model.HallSchemeModel
import com.ticket.booking.data.model.Seat

@Composable
fun HallScreenContent(
    modifier: Modifier = Modifier,
    hallSchemeModel: HallSchemeModel,
    minPrice: Int,
    selectedSeats: List<Seat>,
    totalAmount: Int,
    onSeatClick: (Seat) -> Unit,
    onPayClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            HallHeader(
                sessionDateTime = hallSchemeModel.session_time
            )
            Text(
                text = stringResource(R.string.min_price, minPrice),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Column (
            modifier = Modifier
                .background(colorResource(R.color.hall_bg))
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val cinema = stringResource(R.string.hardcoded_cinema_name)
            Text(
                text = stringResource(R.string.cinema_name, cinema),
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = hallSchemeModel.hall_name,
                style = MaterialTheme.typography.bodyLarge
            )
            SeatTypeLegend(seatsType = hallSchemeModel.seats_type)
            HallMap(
                seats = hallSchemeModel.seats,
                mapWidth = hallSchemeModel.map_width,
                mapHeight = hallSchemeModel.map_height,
                selectedSeats = selectedSeats,
                onSeatClick = onSeatClick
            )
            AnimatedVisibility(
                visible = selectedSeats.isNotEmpty(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                HallFooter(
                    selectedSeatsCount = selectedSeats.size,
                    totalAmount = totalAmount,
                    onPayClick = onPayClick
                )
            }
        }
    }
}
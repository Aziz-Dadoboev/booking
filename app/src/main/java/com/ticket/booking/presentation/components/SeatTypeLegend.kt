package com.ticket.booking.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ticket.booking.R
import com.ticket.booking.data.model.SeatsType

@Composable
fun SeatTypeLegend(
    seatsType: List<SeatsType>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        seatsType.forEach {
            val iconRes = when (it.seat_type) {
                stringResource(R.string.vip) -> R.drawable.seat_1
                stringResource(R.string.comfort) -> R.drawable.seat_2
                stringResource(R.string.standard) -> R.drawable.seat_3
                else -> R.drawable.seat_booked
            }
            SeatTypeItem(
                icon = iconRes,
                price = it.price
            )
        }
    }
}

@Composable
private fun SeatTypeItem(
    icon: Int,
    price: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = painterResource(icon),
            modifier = Modifier
                .size(16.dp),
            contentDescription = null
        )

        Text(
            text = price.toString(),
            style = MaterialTheme.typography.titleMedium
        )
    }
}
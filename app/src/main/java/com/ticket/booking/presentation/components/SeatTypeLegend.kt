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
import androidx.compose.ui.unit.dp
import com.ticket.booking.R

@Composable
fun SeatTypeLegend() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SeatTypeItem(R.drawable.seat_1, "70")
        SeatTypeItem(R.drawable.seat_2, "50")
        SeatTypeItem(R.drawable.seat_3, "45")
    }
}

@Composable
private fun SeatTypeItem(
    icon: Int,
    price: String
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
            text = price,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
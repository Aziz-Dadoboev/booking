package com.ticket.booking.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SeatTypeLegend() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SeatTypeItem("VIP", Color.Yellow, "8000")
        SeatTypeItem("Комфорт", Color.Green, "5500")
        SeatTypeItem("Стандарт", Color.Blue, "6000")
    }
}

@Composable
private fun SeatTypeItem(
    type: String,
    color: Color,
    price: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, shape = RoundedCornerShape(2.dp))
        )
        Column {
            Text(
                text = type,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = price,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
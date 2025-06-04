package com.ticket.booking.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HallHeader(
    modifier: Modifier = Modifier,
    hallName: String = "Зал 3",
    sessionDateTime: String = "16:30, 5 марта 2025"
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = hallName,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = sessionDateTime,
            style = MaterialTheme.typography.bodyMedium
        )
        SeatTypeLegend()
    }
}
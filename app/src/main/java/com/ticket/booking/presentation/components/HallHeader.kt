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
fun HallHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Зал 3",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "16:30, 5 марта 2025",
            style = MaterialTheme.typography.bodyMedium
        )
        SeatTypeLegend()
    }
}
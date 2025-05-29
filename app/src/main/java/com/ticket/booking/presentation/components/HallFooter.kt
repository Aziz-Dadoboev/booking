package com.ticket.booking.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ticket.booking.R

@Composable
fun HallFooter() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Выбрано мест: 0",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Сумма: 0 ₽",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Button(
            onClick = { // TODO: На экран оплаты
            },
            enabled = false
        ) {
            Text(stringResource(R.string.pay))
        }
    }
}
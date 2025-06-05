package com.ticket.booking.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ticket.booking.R

@Composable
fun HallHeader(
    modifier: Modifier = Modifier,
    sessionDateTime: String
) {
    Box(
        modifier = modifier
            .background(
                color = colorResource(R.color.green),
                shape = RoundedCornerShape(8.dp)
            ),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = sessionDateTime,
                style = MaterialTheme.typography.bodyLarge,
                color = colorResource(R.color.white)
            )

            Text(
                text = stringResource(R.string.hardcoded_2d),
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(R.color.white)
            )
        }
    }
}
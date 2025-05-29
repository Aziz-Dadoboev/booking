package com.ticket.booking.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ticket.booking.presentation.components.HallFooter
import com.ticket.booking.presentation.components.HallHeader
import com.ticket.booking.presentation.components.HallMap

@Composable
fun HallScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        HallHeader()

        HallMap(
            modifier = Modifier.weight(1f)
        )
        HallFooter()
    }
}
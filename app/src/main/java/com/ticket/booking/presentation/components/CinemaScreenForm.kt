package com.ticket.booking.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ticket.booking.R

@Composable
fun CinemaScreenForm(
    modifier: Modifier = Modifier,
    screenOffsetX: Dp,
    screenOffsetY: Dp,
    screenWidth: Dp,
    screenHeight: Dp
) {
    val color = colorResource(R.color.gray)
    Canvas(
        modifier = modifier
            .absoluteOffset(x = screenOffsetX, y = screenOffsetY)
            .width(screenWidth)
            .height(screenHeight)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        val width = size.width
        val height = size.height

        val startX = 0f
        val startY = height * 0.8f

        val controlX = width / 2f
        val controlY = height * (-2f)

        val path = Path().apply {
            moveTo(startX, startY)
            quadraticTo(controlX, controlY, width, startY)
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 16.dp.toPx(), cap = StrokeCap.Square)
        )
    }
}

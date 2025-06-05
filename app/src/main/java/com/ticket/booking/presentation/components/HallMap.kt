package com.ticket.booking.presentation.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.ticket.booking.data.model.Seat

@Composable
fun HallMap(
    modifier: Modifier = Modifier,
    seats: List<Seat>,
    mapWidth: Int = 558,
    mapHeight: Int = 346,
    selectedSeats: List<Seat> = emptyList(),
    onSeatClick: (Seat) -> Unit = {}
) {
    val density = LocalDensity.current
    val seatSize = 24.dp
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var zoomCenter by remember { mutableStateOf(Offset.Zero) }

    BoxWithConstraints(
        modifier = modifier
    ) {
        val boxWidth = with(LocalDensity.current) { maxWidth.toPx() }
        val boxHeight = with(LocalDensity.current) { maxHeight.toPx() }

        val initialOffsetX = remember {
            (boxWidth - mapWidth * density.density) / 2
        }
        val initialOffsetY = remember {
            (boxHeight - mapHeight * density.density) / 2
        }

        LaunchedEffect(Unit) {
            offset = Offset(initialOffsetX, initialOffsetY)
        }
        val gestureModifier = Modifier
            .pointerInput(boxWidth to boxHeight) {
                val (width, height) = boxWidth to boxHeight

                detectTransformGestures(
                    onGesture = { centroid, pan, zoom, _ ->
                        val newScale = (scale * zoom).coerceIn(1f, 3f)

                        val contentWidth = mapWidth * density.density
                        val contentHeight = mapHeight * density.density

                        val centerOffsetX = (width - contentWidth) / 2
                        val centerOffsetY = (height - contentHeight) / 2

                        val maxX = ((contentWidth * newScale - width) / 2).coerceAtLeast(0f)
                        val maxY = ((contentHeight * newScale - height) / 2).coerceAtLeast(0f)

                        val newOffsetX = if (newScale > 1f) {
                            (offset.x + pan.x).coerceIn(
                                centerOffsetX - maxX,
                                centerOffsetX + maxX
                            )
                        } else {
                            centerOffsetX
                        }

                        val newOffsetY = if (newScale > 1f) {
                            (offset.y + pan.y).coerceIn(
                                centerOffsetY - maxY,
                                centerOffsetY + maxY
                            )
                        } else {
                            centerOffsetY
                        }
                        scale = (scale * zoom).coerceIn(1f, 5f)
                        offset = Offset(newOffsetX, newOffsetY)
                        zoomCenter = centroid
                    }
                )
            }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clipToBounds()
                .then(gestureModifier)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
                .size(
                    width = (mapWidth * density.density).dp,
                    height = (mapHeight * density.density).dp
                )
        ) {
            seats.forEach { seat ->
                val seatModifier = Modifier
                    .absoluteOffset(
                        x = seat.left.dp,
                        y = seat.top.dp
                    )
                    .size(seatSize)
                    .pointerInput(seat) {
                        detectTapGestures {
                            onSeatClick(seat)
                        }
                    }

                when (seat.object_type) {
                    "seat" -> {
                        val isSelected = selectedSeats.contains(seat)
                        val selectedNum = if (isSelected) {
                            selectedSeats.indexOf(seat) + 1
                        } else {
                            0
                        }
                        SeatItem(
                            modifier = seatModifier,
                            seat = seat,
                            isSelected = isSelected,
                            selectedNum = selectedNum
                        )
                    }
                    "label" -> Text(seat.object_title, seatModifier)
                }
            }
        }
    }
}


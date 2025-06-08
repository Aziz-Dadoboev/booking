package com.ticket.booking.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ticket.booking.R
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
    val seatSize = 24.dp

    var zoom by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var size by remember { mutableStateOf(IntSize.Zero) }

    val gestureModifier = Modifier
        .fillMaxSize()
        .clipToBounds()
        .pointerInput(Unit) {
            detectTransformGestures(
                onGesture = { _, gesturePan, gestureZoom, _ ->
                    zoom = (zoom * gestureZoom).coerceIn(1f, 3f)
                    val newOffset = offset + gesturePan
                    val maxX = (size.width * (zoom - 1) / 2f)
                    val maxY = (size.height * (zoom - 1) / 2f)

                    offset = Offset(
                        newOffset.x.coerceIn(-maxX, maxX),
                        newOffset.y.coerceIn(-maxY, maxY)
                    )
                }
            )
        }.onSizeChanged {
            size = it
        }.graphicsLayer {
            scaleX = zoom
            scaleY = zoom
            translationX = offset.x
            translationY = offset.y
        }

    BoxWithConstraints(
        modifier = Modifier
            .then(gestureModifier)
            .background(Color.LightGray)
        ) {

        val maxWidthPx = with(LocalDensity.current) {maxWidth.toPx()}
        val maxHeightPx = with(LocalDensity.current) {maxHeight.toPx()}

        val scaleX = maxWidthPx / mapWidth
        val scaleY = maxHeightPx / mapHeight

        val screenWidthPx = mapWidth.toFloat() * scaleX
        val screenHeightDp = 12.dp

        val screenOffsetX = ((maxWidthPx - screenWidthPx) / 2f).coerceAtLeast(0f)
        val screenOffsetY = 48.dp

        CinemaScreenForm(
            screenOffsetX = with(LocalDensity.current) { screenOffsetX.toDp() },
            screenOffsetY = screenOffsetY,
            screenWidth = with(LocalDensity.current) { screenWidthPx.toDp() },
            screenHeight = screenHeightDp
        )

        Text(
            text = stringResource(R.string.screen),
            color = Color.Black,
            fontSize = 14.sp,
            modifier = Modifier
                .absoluteOffset(
                    x = with(LocalDensity.current) { screenOffsetX.toDp() },
                    y = screenOffsetY + screenHeightDp + 2.dp
                )
                .width(with(LocalDensity.current) { screenWidthPx.toDp() }),
            textAlign = TextAlign.Center
        )


        seats.forEach { seat ->
            val scaledX = seat.left * scaleX
            val scaledY = seat.top * scaleY
            Log.d("XY", "$scaledX, $scaledY, $maxWidth, $maxHeight")

            val seatModifier = Modifier
                .absoluteOffset(
                    x = with(LocalDensity.current) { scaledX.toDp() },
                    y = with(LocalDensity.current) { scaledY.toDp() }
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
                "label" -> Text(
                    text = seat.object_title,
                    modifier = seatModifier
                )
            }
        }
    }
}


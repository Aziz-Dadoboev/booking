package com.ticket.booking.presentation.components

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
import com.ticket.booking.domain.model.Coordinates
import com.ticket.booking.domain.model.Seat

@Composable
fun HallMap(
    modifier: Modifier = Modifier,
    seats: List<Seat> = mockSeats,
    mapWidth: Int = 558,
    mapHeight: Int = 346,
) {
    val density = LocalDensity.current
    val seatSize = 24.dp
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var zoomCenter by remember { mutableStateOf(Offset.Zero) }

    BoxWithConstraints(
        modifier = Modifier
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clipToBounds()
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
                                (offset.x + pan.x).coerceIn(centerOffsetX - maxX, centerOffsetX + maxX)
                            } else {
                                centerOffsetX
                            }

                            val newOffsetY = if (newScale > 1f) {
                                (offset.y + pan.y).coerceIn(centerOffsetY - maxY, centerOffsetY + maxY)
                            } else {
                                centerOffsetY
                            }
                            scale = (scale * zoom).coerceIn(1f, 5f)
                            offset = Offset(newOffsetX, newOffsetY)
                            zoomCenter = centroid
                        }
                    )
                }
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
                        x = seat.coordinates.left.dp,
                        y = seat.coordinates.top.dp
                    )
                    .size(seatSize)

                when (seat.objectType) {
                    "seat" -> SeatItem(seatModifier, seat, onSeatClick = {})
                    "label" -> Text(seat.objectTitle ?: "", seatModifier)
                }
            }

        }
    }
}

private val mockSeats =  listOf(
    Seat(
        id = 7717241,
        sector = "6",
        row = "1",
        place = "1",
        coordinates = Coordinates(top = 150, left = 160),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/1_1.jpg",
        placeName = null,
        seatType = "VIP",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:1, место: 1",
        objectTitle = "1"
    ),
    Seat(
        id = 7717265,
        sector = null,
        row = "",
        place = "",
        coordinates = Coordinates(top = 150, left = 106),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/placeholder.png",
        placeName = null,
        seatType = null,
        objectType = "label",
        objectDescription = "",
        objectTitle = "1"
    ),
    Seat(
        id = 7717266,
        sector = null,
        row = "",
        place = "",
        coordinates = Coordinates(top = 150, left = 414),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/placeholder.png",
        placeName = null,
        seatType = null,
        objectType = "label",
        objectDescription = "",
        objectTitle = "1"
    ),
    Seat(
        id = 7717242,
        sector = "6",
        row = "1",
        place = "2",
        coordinates = Coordinates(top = 150, left = 200),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/1_2.jpg",
        placeName = null,
        seatType = "VIP",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:1, место: 2",
        objectTitle = "2"
    ),
    Seat(
        id = 7717243,
        sector = "6",
        row = "1",
        place = "3",
        coordinates = Coordinates(top = 150, left = 240),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/1_3.jpg",
        placeName = null,
        seatType = "VIP",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:1, место: 3",
        objectTitle = "3"
    ),
    Seat(
        id = 7717244,
        sector = "6",
        row = "1",
        place = "4",
        coordinates = Coordinates(top = 150, left = 280),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/1_4.jpg",
        placeName = null,
        seatType = "VIP",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:1, место: 4",
        objectTitle = "4"
    ),
    Seat(
        id = 7717245,
        sector = "6",
        row = "1",
        place = "5",
        coordinates = Coordinates(top = 150, left = 320),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/1_5.jpg",
        placeName = null,
        seatType = "VIP",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:1, место: 5",
        objectTitle = "5"
    ),
    Seat(
        id = 7717246,
        sector = "6",
        row = "1",
        place = "6",
        coordinates = Coordinates(top = 150, left = 360),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/1_6.jpg",
        placeName = null,
        seatType = "VIP",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:1, место: 6",
        objectTitle = "6"
    ),
    Seat(
        id = 7717247,
        sector = "6",
        row = "2",
        place = "1",
        coordinates = Coordinates(top = 190, left = 160),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/2_1.jpg",
        placeName = null,
        seatType = "COMFORT",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:2, место: 1",
        objectTitle = "1"
    ),
    Seat(
        id = 7717268,
        sector = null,
        row = "",
        place = "",
        coordinates = Coordinates(top = 190, left = 414),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/placeholder.png",
        placeName = null,
        seatType = null,
        objectType = "label",
        objectDescription = "",
        objectTitle = "2"
    ),
    Seat(
        id = 7717248,
        sector = "6",
        row = "2",
        place = "2",
        coordinates = Coordinates(top = 190, left = 200),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/2_2.jpg",
        placeName = null,
        seatType = "COMFORT",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:2, место: 2",
        objectTitle = "2"
    ),
    // Продолжение списка...
    Seat(
        id = 7717267,
        sector = null,
        row = "",
        place = "",
        coordinates = Coordinates(top = 190, left = 106),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/placeholder.png",
        placeName = null,
        seatType = null,
        objectType = "label",
        objectDescription = "",
        objectTitle = "2"
    ),
    Seat(
        id = 7717249,
        sector = "6",
        row = "2",
        place = "3",
        coordinates = Coordinates(top = 190, left = 240),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/2_3.jpg",
        placeName = null,
        seatType = "COMFORT",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:2, место: 3",
        objectTitle = "3"
    ),
    Seat(
        id = 7717250,
        sector = "6",
        row = "2",
        place = "4",
        coordinates = Coordinates(top = 190, left = 280),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/2_4.jpg",
        placeName = null,
        seatType = "COMFORT",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:2, место: 4",
        objectTitle = "4"
    ),
    Seat(
        id = 7717251,
        sector = "6",
        row = "2",
        place = "5",
        coordinates = Coordinates(top = 190, left = 320),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/2_5.jpg",
        placeName = null,
        seatType = "COMFORT",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:2, место: 5",
        objectTitle = "5"
    ),
    Seat(
        id = 7717252,
        sector = "6",
        row = "2",
        place = "6",
        coordinates = Coordinates(top = 190, left = 360),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/2_6.jpg",
        placeName = null,
        seatType = "COMFORT",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:2, место: 6",
        objectTitle = "6"
    ),
    Seat(
        id = 7717253,
        sector = "6",
        row = "3",
        place = "1",
        coordinates = Coordinates(top = 230, left = 160),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/3_1.jpg",
        placeName = null,
        seatType = "COMFORT",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:3, место: 1",
        objectTitle = "1"
    ),
    Seat(
        id = 7717254,
        sector = "6",
        row = "3",
        place = "2",
        coordinates = Coordinates(top = 230, left = 200),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/3_2.jpg",
        placeName = null,
        seatType = "COMFORT",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:3, место: 2",
        objectTitle = "2"
    ),
    Seat(
        id = 7717269,
        sector = null,
        row = "",
        place = "",
        coordinates = Coordinates(top = 230, left = 106),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/placeholder.png",
        placeName = null,
        seatType = null,
        objectType = "label",
        objectDescription = "",
        objectTitle = "3"
    ),
    Seat(
        id = 7717270,
        sector = null,
        row = "",
        place = "",
        coordinates = Coordinates(top = 230, left = 374),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/placeholder.png",
        placeName = null,
        seatType = null,
        objectType = "label",
        objectDescription = "",
        objectTitle = "3"
    ),
    Seat(
        id = 7717255,
        sector = "6",
        row = "3",
        place = "3",
        coordinates = Coordinates(top = 230, left = 240),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/3_3.jpg",
        placeName = null,
        seatType = "COMFORT",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:3, место: 3",
        objectTitle = "3"
    ),
    Seat(
        id = 7717256,
        sector = "6",
        row = "3",
        place = "4",
        coordinates = Coordinates(top = 230, left = 280),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/3_4.jpg",
        placeName = null,
        seatType = "COMFORT",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:3, место: 4",
        objectTitle = "4"
    ),
    Seat(
        id = 7717257,
        sector = "6",
        row = "3",
        place = "5",
        coordinates = Coordinates(top = 230, left = 320),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/3_5.jpg",
        placeName = null,
        seatType = "COMFORT",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:3, место: 5",
        objectTitle = "5"
    ),
    Seat(
        id = 7717258,
        sector = "6",
        row = "4",
        place = "1",
        coordinates = Coordinates(top = 270, left = 160),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/4_1.jpg",
        placeName = null,
        seatType = "STANDARD",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:4, место: 1",
        objectTitle = "1"
    ),
    Seat(
        id = 7717259,
        sector = "6",
        row = "4",
        place = "2",
        coordinates = Coordinates(top = 270, left = 200),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/4_2.jpg",
        placeName = null,
        seatType = "STANDARD",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:4, место: 2",
        objectTitle = "2"
    ),
    Seat(
        id = 7717260,
        sector = "6",
        row = "4",
        place = "3",
        coordinates = Coordinates(top = 270, left = 240),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/4_3.jpg",
        placeName = null,
        seatType = "STANDARD",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:4, место: 3",
        objectTitle = "3"
    ),
    Seat(
        id = 7717261,
        sector = "6",
        row = "4",
        place = "4",
        coordinates = Coordinates(top = 270, left = 280),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/4_4.jpg",
        placeName = null,
        seatType = "STANDARD",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:4, место: 4",
        objectTitle = "4"
    ),
    Seat(
        id = 7717271,
        sector = null,
        row = "",
        place = "",
        coordinates = Coordinates(top = 270, left = 106),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/placeholder.png",
        placeName = null,
        seatType = null,
        objectType = "label",
        objectDescription = "",
        objectTitle = "4"
    ),
    Seat(
        id = 7717272,
        sector = null,
        row = "",
        place = "",
        coordinates = Coordinates(top = 270, left = 454),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/placeholder.png",
        placeName = null,
        seatType = null,
        objectType = "label",
        objectDescription = "",
        objectTitle = "4"
    ),
    Seat(
        id = 7717262,
        sector = "6",
        row = "4",
        place = "5",
        coordinates = Coordinates(top = 270, left = 320),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/4_5.jpg",
        placeName = null,
        seatType = "STANDARD",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:4, место: 5",
        objectTitle = "5"
    ),
    Seat(
        id = 7717263,
        sector = "6",
        row = "4",
        place = "6",
        coordinates = Coordinates(top = 270, left = 360),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/4_6.jpg",
        placeName = null,
        seatType = "STANDARD",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:4, место: 6",
        objectTitle = "6"
    ),
    Seat(
        id = 7717264,
        sector = "6",
        row = "4",
        place = "7",
        coordinates = Coordinates(top = 270, left = 400),
        bookedSeats = 0,
        seatView = "https://echipta.tj/app/web/upload/seats_views/org6/hall54/4_7.jpg",
        placeName = null,
        seatType = "STANDARD",
        objectType = "seat",
        objectDescription = "Сектор:6, ряд:4, место: 7",
        objectTitle = "7"
    )
)

/*

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

.pointerInput(boxWidth to boxHeight)
                {
                    val (width, height) = boxWidth to boxHeight

//                    detectTransformGestures(
//                        onGesture = { centroid, pan, zoom, _ ->
//                            val newScale = (scale * zoom).coerceIn(startScale, 3f)
//
//                            val contentWidth = mapWidth * density.density
//                            val contentHeight = mapHeight * density.density
//
//                            val centerOffsetX = (width - contentWidth) / 2
//                            val centerOffsetY = (height - contentHeight) / 2
//
//                            val maxX = ((contentWidth * newScale - width) / 2).coerceAtLeast(0f)
//                            val maxY = ((contentHeight * newScale - height) / 2).coerceAtLeast(0f)
//
//                            val newOffsetX = if (newScale > startScale) {
//                                (offset.x + pan.x).coerceIn(centerOffsetX - maxX, centerOffsetX + maxX)
//                            } else {
//                                centerOffsetX
//                            }
//
//                            val newOffsetY = if (newScale > startScale) {
//                                (offset.y + pan.y).coerceIn(centerOffsetY - maxY, centerOffsetY + maxY)
//                            } else {
//                                centerOffsetY
//                            }



//                            scale = (scale * zoom).coerceIn(1f, 5f)
//                            offset = Offset(newOffsetX, newOffsetY)
//                            zoomCenter = centroid
//                        }
//                    )
                }
//                .graphicsLayer {
//                    scaleX = scale
//                    scaleY = scale
//                    translationX = offset.x
//                    translationY = offset.y
//                }

 */
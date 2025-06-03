package com.ticket.booking.domain.model

data class Seat(
    val id: Long,
    val sector: String?,
    val row: String,
    val place: String,
    val coordinates: Coordinates,
    val bookedSeats: Int,
    val seatView: String,
    val placeName: String?,
    val seatType: String?,
    val objectType: String,
    val objectDescription: String?,
    val objectTitle: String
)

data class Coordinates(
    val top: Int,
    val left: Int
)
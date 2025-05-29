package com.ticket.booking.domain.model

data class Seat(
    val id: Long,
    val sector: String?,
    val row: String,
    val place: String,
    val coordinates: Coordinates,
    val isBooked: Boolean,
    val seatView: String,
    val seatType: String?,
    val objectType: String,
    val description: String
)

data class Coordinates(
    val top: Int,
    val left: Int
)
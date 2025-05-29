package com.ticket.booking.domain.model

data class HallScheme(
    val sessionDate: String,
    val sessionTime: String,
    val mapWidth: Int,
    val mapHeight: Int,
    val hallName: String,
    val hasStarted: Boolean,
    val seats: List<Seat>,
    val seatsType: List<SeatType>
)

package com.ticket.booking.domain.model

data class HallScheme(
    val sessionDate: String,
    val sessionTime: String,
    val mapWidth: Int,
    val mapHeight: Int,
    val hallName: String,
    val merchantId: Int,
    val hasOrzu: Boolean,
    val hasStarted: Boolean,
    val hasStartedText: String,
    val seats: List<Seat>,
    val seatsType: List<SeatsType>
)

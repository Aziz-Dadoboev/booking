package com.ticket.booking.domain.model

data class SeatType(
    val id: Long,
    val ticketType: String,
    val name: String,
    val price: Double,
    val seatType: String
)

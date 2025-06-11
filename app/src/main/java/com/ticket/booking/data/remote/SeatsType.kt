package com.ticket.booking.data.remote

data class SeatsType(
    val name: String,
    val price: Int,
    val seat_type: String,
    val ticket_id: Int,
    val ticket_type: String
)
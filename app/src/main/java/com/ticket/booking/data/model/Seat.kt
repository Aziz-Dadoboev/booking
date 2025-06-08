package com.ticket.booking.data.model

data class Seat(
    val booked_seats: Int,
    val left: Int,
    val object_description: String,
    val object_title: String,
    val object_type: String,
    val place: String,
    val place_name: Any,
    val row_num: String,
    val seat_id: Int,
    val seat_type: String,
    val seat_view: String,
    val sector: String,
    val top: Int
)
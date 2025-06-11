package com.ticket.hallmapview

data class HallMap(
    val hall_name: String,
    val has_orzu: Boolean,
    val has_started: Boolean,
    val has_started_text: String,
    val map_height: Int,
    val map_width: Int,
    val merchant_id: Int,
    val seats: List<Seat>,
    val seats_type: List<SeatsType>,
    val session_date: String,
    val session_time: String
)
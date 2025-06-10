package com.ticket.booking.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seats")
data class SeatEntity(
    @PrimaryKey val seatId: Int,
    val sector: String?,
    val rowNum: String,
    val place: String,
    val top: Int,
    val left: Int,
    val bookedSeats: Int,
    val seatView: String,
    val placeName: String?,
    val seatType: String?,
    val objectType: String,
    val objectDescription: String,
    val objectTitle: String,
    val sessionId: String
)


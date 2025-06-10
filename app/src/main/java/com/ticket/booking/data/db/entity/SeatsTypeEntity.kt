package com.ticket.booking.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seats_type")
data class SeatsTypeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val price: Int,
    val seatType: String,
    val ticketId: Int,
    val ticketType: String,
    val sessionId: String
)


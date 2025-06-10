package com.ticket.booking.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey val sessionId: String,
    val sessionDate: String,
    val sessionTime: String,
    val mapWidth: Int,
    val mapHeight: Int,
    val hallName: String,
    val merchantId: Int,
    val hasOrzu: Boolean,
    val hasStarted: Boolean,
    val hasStartedText: String
)


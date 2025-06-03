package com.ticket.booking.domain.model

enum class SeatCategory {
    VIP,
    COMFORT,
    STANDARD,
    SELECTED,
    BOOKED;

    companion object {
        fun fromString(type: String?): SeatCategory =
            when (type) {
                "VIP" -> VIP
                "COMFORT" -> COMFORT
                "STANDARD" -> STANDARD
                else -> BOOKED
            }
    }
}
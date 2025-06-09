package com.ticket.booking.domain

import com.ticket.booking.data.HallResponse

interface BookingRepository {
    suspend fun getHallScheme(): Result<HallResponse>
}
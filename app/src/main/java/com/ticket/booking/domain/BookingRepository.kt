package com.ticket.booking.domain

import com.ticket.booking.data.remote.HallResponse

interface BookingRepository {
    suspend fun getHallScheme(): Result<HallResponse>
    suspend fun getCachedHallScheme(sessionId: String) : Result<HallResponse>
}
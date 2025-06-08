package com.ticket.booking.domain.repository

import com.ticket.booking.data.model.HallSchemeModel

interface HallRepository {
    suspend fun getHallScheme(): Result<HallSchemeModel>
}
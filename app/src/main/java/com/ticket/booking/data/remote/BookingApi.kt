package com.ticket.booking.data.remote

import com.ticket.booking.data.model.HallSchemeModel
import retrofit2.http.GET

interface BookingApi {

    @GET("test11/seat.json")
    suspend fun getDataFromNetwork() : HallSchemeModel
}
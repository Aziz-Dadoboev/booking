package com.ticket.booking.data

import retrofit2.http.GET

interface BookingApi {

    @GET("test11/seat.json")
    suspend fun getDataFromNetwork() : HallResponse
}
package com.ticket.booking.data.remote

import retrofit2.http.GET

interface BookingApi {

    @GET("test11/seat.json")
    suspend fun getDataFromNetwork() : HallResponse
}
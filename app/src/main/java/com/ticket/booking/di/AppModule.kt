package com.ticket.booking.di

import com.ticket.booking.data.remote.BookingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import java.util.logging.Logger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBookingApi(retrofit: Retrofit): BookingApi {
        return retrofit.create(BookingApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLogger(): Logger {
        return Logger.getLogger("BookingLogger")
    }
}
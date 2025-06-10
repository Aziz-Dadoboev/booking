package com.ticket.booking.di

import com.ticket.booking.data.remote.BookingRepositoryImpl
import com.ticket.booking.domain.BookingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsBookingRepository(
        bookingRepositoryImpl: BookingRepositoryImpl
    ): BookingRepository
}
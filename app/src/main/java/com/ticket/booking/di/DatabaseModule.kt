package com.ticket.booking.di

import android.content.Context
import androidx.room.Room
import com.ticket.booking.data.db.BookingDatabase
import com.ticket.booking.data.db.dao.SeatDao
import com.ticket.booking.data.db.dao.SeatsTypeDao
import com.ticket.booking.data.db.dao.SessionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideBookingDatabase(@ApplicationContext context: Context): BookingDatabase {
        return Room.databaseBuilder(
            context,
            BookingDatabase::class.java,
            "booking_database"
        ).build()
    }

    @Provides
    fun provideSessionDao(db: BookingDatabase): SessionDao = db.sessionDao()

    @Provides
    fun provideSeatsDao(db: BookingDatabase): SeatDao = db.seatDao()

    @Provides
    fun provideSeatsTypeDao(db: BookingDatabase): SeatsTypeDao = db.seatsTypeDao()
}
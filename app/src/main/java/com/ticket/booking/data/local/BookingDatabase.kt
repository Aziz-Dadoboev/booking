package com.ticket.booking.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ticket.booking.data.local.dao.SeatDao
import com.ticket.booking.data.local.dao.SeatsTypeDao
import com.ticket.booking.data.local.dao.SessionDao
import com.ticket.booking.data.local.entity.SeatEntity
import com.ticket.booking.data.local.entity.SeatsTypeEntity
import com.ticket.booking.data.local.entity.SessionEntity

@Database(
    entities = [SessionEntity::class, SeatEntity::class, SeatsTypeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BookingDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun seatDao(): SeatDao
    abstract fun seatsTypeDao(): SeatsTypeDao
}

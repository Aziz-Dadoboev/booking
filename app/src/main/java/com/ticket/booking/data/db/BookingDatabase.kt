package com.ticket.booking.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ticket.booking.data.db.dao.SeatDao
import com.ticket.booking.data.db.dao.SeatsTypeDao
import com.ticket.booking.data.db.dao.SessionDao
import com.ticket.booking.data.db.entity.SeatEntity
import com.ticket.booking.data.db.entity.SeatsTypeEntity
import com.ticket.booking.data.db.entity.SessionEntity

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

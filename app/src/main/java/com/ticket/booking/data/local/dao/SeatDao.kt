package com.ticket.booking.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ticket.booking.data.local.entity.SeatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SeatDao {
    @Query("SELECT * FROM seats WHERE sessionId = :sessionId")
    suspend fun getSeatsBySessionId(sessionId: String): List<SeatEntity>

    @Query("SELECT * FROM seats")
    fun getSeats(): Flow<List<SeatEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeat(seat: SeatEntity)

    @Delete
    suspend fun deleteSeat(seat: SeatEntity)
}
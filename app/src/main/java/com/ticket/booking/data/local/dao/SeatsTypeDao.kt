package com.ticket.booking.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ticket.booking.data.local.entity.SeatsTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SeatsTypeDao {
    @Query("SELECT * FROM seats_type WHERE sessionId = :sessionId")
    suspend fun getSeatsTypeBySessionId(sessionId: String): List<SeatsTypeEntity>

    @Query("SELECT * FROM seats_type")
    fun getSeatsType(): Flow<List<SeatsTypeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeatsType(seatsType: SeatsTypeEntity)

    @Delete
    suspend fun deleteSeatsType(seatsType: SeatsTypeEntity)
}
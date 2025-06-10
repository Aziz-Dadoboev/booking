package com.ticket.booking.data.remote

import com.ticket.booking.data.db.dao.SeatDao
import com.ticket.booking.data.db.dao.SeatsTypeDao
import com.ticket.booking.data.db.dao.SessionDao
import com.ticket.booking.data.db.entity.SeatEntity
import com.ticket.booking.data.db.entity.SeatsTypeEntity
import com.ticket.booking.data.db.entity.SessionEntity
import com.ticket.booking.domain.BookingRepository
import retrofit2.HttpException
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookingRepositoryImpl @Inject constructor(
    private val bookingApi: BookingApi,
    private val sessionDao: SessionDao,
    private val seatDao: SeatDao,
    private val seatsTypeDao: SeatsTypeDao,
    private val logger: Logger
): BookingRepository {
    override suspend fun getHallScheme(): Result<HallResponse> {
        return try {
            logger.log(Level.INFO, "network request")
            val result = bookingApi.getDataFromNetwork()
            logger.log(Level.INFO, "success. caching data...")
            cacheHallResponse(result)
            logger.log(Level.INFO, "data cached successfully")
            Result.success(result)
        } catch (e: HttpException) {
            logger.log(Level.INFO, "http: ${e.code()} ${e.message()}")
            Result.failure(Exception("http: ${e.code()} ${e.message()}"))
        } catch (e: IOException) {
            logger.log(Level.INFO, "IO: ${e.message}")
            Result.failure(Exception("IO: ${e.message}"))
        } catch (e: Exception) {
            logger.log(Level.INFO, "unknown: ${e.message}")
            Result.failure(Exception("error: ${e.message}"))
        }
    }

    private suspend fun cacheHallResponse(hall: HallResponse) {
        val sessionId = "${hall.session_date}_${hall.session_time}"

        val session = SessionEntity(
            sessionId = sessionId,
            sessionDate = hall.session_date,
            sessionTime = hall.session_time,
            mapWidth = hall.map_width,
            mapHeight = hall.map_height,
            hallName = hall.hall_name,
            merchantId = hall.merchant_id,
            hasOrzu = hall.has_orzu,
            hasStarted = hall.has_started,
            hasStartedText = hall.has_started_text
        )
        sessionDao.insertSession(session)

        val seats = hall.seats.map {
            SeatEntity(
                seatId = it.seat_id,
                sector = it.sector,
                rowNum = it.row_num,
                place = it.place,
                top = it.top,
                left = it.left,
                bookedSeats = it.booked_seats,
                seatView = it.seat_view,
                placeName = it.place_name.toString(),
                seatType = it.seat_type,
                objectType = it.object_type,
                objectDescription = it.object_description,
                objectTitle = it.object_title,
                sessionId = sessionId
            )
        }
        seats.forEach { seatDao.insertSeat(it) }

        val seatsTypes = hall.seats_type.map {
            SeatsTypeEntity(
                name = it.name,
                price = it.price,
                seatType = it.seat_type,
                ticketId = it.ticket_id,
                ticketType = it.ticket_type,
                sessionId = sessionId
            )
        }
        seatsTypes.forEach { seatsTypeDao.insertSeatsType(it) }
    }

    override suspend fun getCachedHallScheme(sessionId: String): Result<HallResponse> {
        return try {
            val session = sessionDao.getSessionById(sessionId)
            val seats = seatDao.getSeatsBySessionId(sessionId)
            val seatsTypes = seatsTypeDao.getSeatsTypeBySessionId(sessionId)

            if (session == null) return Result.failure(Exception("Session not found"))

            val hallResponse = HallResponse(
                session_date = session.sessionDate,
                session_time = session.sessionTime,
                map_width = session.mapWidth,
                map_height = session.mapHeight,
                hall_name = session.hallName,
                merchant_id = session.merchantId,
                has_orzu = session.hasOrzu,
                has_started = session.hasStarted,
                has_started_text = session.hasStartedText,
                seats = seats.map {
                    Seat(
                        seat_id = it.seatId,
                        sector = it.sector ?: "",
                        row_num = it.rowNum,
                        place = it.place,
                        top = it.top,
                        left = it.left,
                        booked_seats = it.bookedSeats,
                        seat_view = it.seatView,
                        place_name = it.placeName ?: "",
                        seat_type = it.seatType ?: "",
                        object_type = it.objectType,
                        object_description = it.objectDescription,
                        object_title = it.objectTitle
                    )
                },
                seats_type = seatsTypes.map {
                    SeatsType(
                        name = it.name,
                        price = it.price,
                        seat_type = it.seatType,
                        ticket_id = it.ticketId,
                        ticket_type = it.ticketType
                    )
                }
            )

            Result.success(hallResponse)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
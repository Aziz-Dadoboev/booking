package com.ticket.booking.data.repository

import com.ticket.booking.data.model.HallSchemeModel
import com.ticket.booking.data.remote.BookingApi
import com.ticket.booking.domain.repository.HallRepository
import retrofit2.HttpException
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HallRepositoryImpl @Inject constructor(
    private val bookingApi: BookingApi,
    private val logger: Logger
) : HallRepository {
    override suspend fun getHallScheme(): Result<HallSchemeModel> {
        return try {
            val result = bookingApi.getDataFromNetwork()
            logger.log(Level.INFO, "success: $result")
            Result.success(result)
        } catch (e: HttpException) {
            Result.failure(Exception("http: ${e.code()} ${e.message()}"))
        } catch (e: IOException) {
            Result.failure(Exception("io: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("unknown: ${e.message}"))
        }
    }
}
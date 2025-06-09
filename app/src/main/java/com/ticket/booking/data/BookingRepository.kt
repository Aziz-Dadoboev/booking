package com.ticket.booking.data

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
    private val logger: Logger
): BookingRepository {
    override suspend fun getHallScheme(): Result<HallResponse> {
        return try {
            val result = bookingApi.getDataFromNetwork()
            logger.log(Level.INFO, "success")
            Result.success(result)
        } catch (e: HttpException) {
            Result.failure(Exception("http: ${e.code()} ${e.message()}"))
        } catch (e: IOException) {
            Result.failure(Exception("IO: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("error: ${e.message}"))
        }
    }
}
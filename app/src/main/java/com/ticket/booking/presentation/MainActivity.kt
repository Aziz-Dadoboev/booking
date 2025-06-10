package com.ticket.booking.presentation

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.ticket.booking.R
import com.ticket.booking.domain.BookingRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var bookingRepository: BookingRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        if (!::bookingRepository.isInitialized) {
            Log.e("MainActivity", "Repository not injected!")
            return
        }
        val textViewResult = findViewById<TextView>(R.id.textViewResult)

        lifecycleScope.launch {
            val result = try {
                bookingRepository.getCachedHallScheme(
                    sessionId = "2025-03-05_16:30"
                )
            } catch (e: Exception) {
                Log.e("MainActivity", "Request failed", e)
                Result.failure(e)
            }
            withContext(Dispatchers.Main) {
                if (result.isSuccess) {
                    textViewResult.text = "Success!"
                    Log.d("MainActivity", "SUCCESS")
                } else {
                    val errorMsg = result.exceptionOrNull()?.message ?: "Unknown error"
                    textViewResult.text = "Error: $errorMsg"
                    Log.e("MainActivity", "FAIL: $errorMsg")
                }
            }
        }
    }
}

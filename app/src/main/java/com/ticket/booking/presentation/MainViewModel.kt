package com.ticket.booking.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ticket.booking.data.remote.HallResponse
import com.ticket.booking.data.remote.SeatsType
import com.ticket.booking.data.toUiSeat
import com.ticket.booking.domain.BookingRepository
import com.ticket.hallmapview.Seat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BookingRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<HallUiState>(HallUiState.Loading)
    val uiState: StateFlow<HallUiState> = _uiState

    private val _currentSessionId = MutableStateFlow<String?>("2025-03-05_16:30")
    val currentSessionId: StateFlow<String?> = _currentSessionId

    private val _preparedUi = MutableStateFlow<PreparedHallUi?>(null)
    val preparedUi: StateFlow<PreparedHallUi?> = _preparedUi

    private val _totalAmount = MutableStateFlow(0)
    val totalAmount: StateFlow<Int> = _totalAmount

    fun loadHallScheme() {
        viewModelScope.launch {
            _uiState.value = HallUiState.Loading

            repository.getHallScheme()
                .onSuccess { hallResponse ->
                    _currentSessionId.value = "${hallResponse.session_date}_${hallResponse.session_time}"
                    _uiState.value = HallUiState.Success(hallResponse)
                }
                .onFailure { error ->
                    _uiState.value = HallUiState.Error(error.message ?: "Unknown error")

                    currentSessionId.value?.let { sessionId ->
                        loadCachedData(sessionId)
                    }
                }
        }
    }

    fun loadCachedData(sessionId: String) {
        viewModelScope.launch {
            _uiState.value = HallUiState.Loading

            repository.getCachedHallScheme(sessionId)
                .onSuccess { hallResponse ->
                    _uiState.value = HallUiState.Success(hallResponse)
                }
                .onFailure {
                    _uiState.value = HallUiState.Error("No cached data available")
                }
        }
    }

    fun prepareUi(hall: HallResponse): PreparedHallUi {
        val uiSeats = hall.seats.map { it.toUiSeat() }
        val seatTypes = hall.seats_type + SeatsType(
            name = "Занято",
            price = 0,
            seat_type = "",
            ticket_id = 0,
            ticket_type = ""
        )

        return PreparedHallUi(
            uiSeats = uiSeats,
            seatTypes = seatTypes,
            hallName = hall.hall_name,
            sessionTime = hall.session_time,
            mapWidth = hall.map_width,
            mapHeight = hall.map_height,
            hasStarted = hall.has_started,
            hasStartedText = hall.has_started_text ?: "",
            bookedSeats = hall.seats.size
        )
    }


    fun calculateFooterState(selectedSeats: List<Seat>): FooterUiState {
        val count = selectedSeats.size
        val total = selectedSeats.sumOf { seatPrice(it) }
        _totalAmount.value = total

        return FooterUiState(
            count = count,
            total = total,
            visible = count > 0
        )
    }

    private fun seatPrice(seat: Seat): Int {
        return when (seat.seat_type) {
            "VIP" -> 1000
            "COMFORT" -> 700
            "STANDARD" -> 500
            else -> 0
        }
    }

}

data class PreparedHallUi(
    val uiSeats: List<Seat>,
    val seatTypes: List<SeatsType>,
    val hallName: String,
    val sessionTime: String,
    val mapWidth: Int,
    val mapHeight: Int,
    val hasStarted: Boolean,
    val hasStartedText: String,
    val bookedSeats: Int,
)

data class FooterUiState(
    val count: Int,
    val total: Int,
    val visible: Boolean
)


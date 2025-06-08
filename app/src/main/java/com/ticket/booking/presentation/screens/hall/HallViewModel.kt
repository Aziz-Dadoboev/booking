package com.ticket.booking.presentation.screens.hall

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ticket.booking.data.model.HallSchemeModel
import com.ticket.booking.data.model.Seat
import com.ticket.booking.data.model.SeatsType
import com.ticket.booking.domain.repository.HallRepository
import com.ticket.booking.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HallViewModel @Inject constructor(
    private val hallRepository: HallRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HallScreenState())
    val state: StateFlow<HallScreenState> = _state.asStateFlow()

    private val _navigation = Channel<String>()
    val navigation = _navigation.receiveAsFlow()

    private val _seats = mutableStateListOf<SeatsType>()
    val minSeatPrice by derivedStateOf {
        _seats.minOfOrNull { it.price } ?: 0
    }

    private val _selectedSeats = mutableStateListOf<Seat>()
    val selectedSeats: List<Seat> = _selectedSeats

    private val _totalAmount = MutableStateFlow(0)
    val totalAmount: StateFlow<Int> = _totalAmount.asStateFlow()

    private val _commissionPercent = MutableStateFlow(0)
    val commissionPercent: StateFlow<Int> = _commissionPercent.asStateFlow()

    init {
        loadHallScheme()
    }

    private fun loadHallScheme() {
        viewModelScope.launch {
            val result = hallRepository.getHallScheme()
            if (result.isSuccess) {
                result.getOrNull()?.let { scheme ->
                    _seats.clear()
                    _seats.addAll(scheme.seats_type)
                }
                _state.update {
                    it.copy(
                        hallScheme = result.getOrNull(),
                        isLoading = false
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        error = result.exceptionOrNull()?.message ?: "Error",
                        isLoading = false
                    )
                }
            }
        }
    }
    fun onSeatClick(seat: Seat) {
        if (_selectedSeats.contains(seat)) {
            _selectedSeats.remove(seat)
        } else {
            _selectedSeats.add(seat)
        }
        _totalAmount.value = _selectedSeats.sumOf { seat ->
            _seats.find { it.seat_type == seat.seat_type }?.price ?: 0
        }
    }


    fun onPayment(comission: Int) {
        _commissionPercent.value = comission
        viewModelScope.launch {
            _navigation.send(Screen.Payment.route)
        }
    }
}

data class HallScreenState(
    val hallScheme: HallSchemeModel? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

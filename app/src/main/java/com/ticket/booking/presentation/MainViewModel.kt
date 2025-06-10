package com.ticket.booking.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ticket.booking.domain.BookingRepository
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

    private val _currentSessionId = MutableStateFlow<String?>(null)
    val currentSessionId: StateFlow<String?> = _currentSessionId

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
}

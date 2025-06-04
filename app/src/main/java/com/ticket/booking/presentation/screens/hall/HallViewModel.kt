package com.ticket.booking.presentation.screens.hall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ticket.booking.data.model.HallSchemeModel
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

    init {
        loadHallScheme()
    }

    private fun loadHallScheme() {
        viewModelScope.launch {
            val result = hallRepository.getHallScheme()
            if (result.isSuccess) {
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

    fun onPayClick() {
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

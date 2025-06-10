package com.ticket.booking.presentation

import com.ticket.booking.data.remote.HallResponse

sealed class HallUiState {
    data object Loading : HallUiState()
    data class Success(val data: HallResponse) : HallUiState()
    data class Error(val message: String) : HallUiState()
}
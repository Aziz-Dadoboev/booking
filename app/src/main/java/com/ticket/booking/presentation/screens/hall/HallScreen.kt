package com.ticket.booking.presentation.screens.hall

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ticket.booking.R
import com.ticket.booking.presentation.components.HallScreenContent

@Composable
fun HallScreen(
    modifier: Modifier = Modifier,
    viewModel: HallViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigation.collect { route ->
            navController.navigate(route)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }
            state.hallScheme != null -> {
                HallScreenContent(
                    modifier = Modifier.fillMaxSize(),
                    hallSchemeModel = state.hallScheme!!,
                    minPrice = viewModel.minSeatPrice,
                    selectedSeats = viewModel.selectedSeats,
                    totalAmount = viewModel.totalAmount.collectAsState().value,
                    onSeatClick = { viewModel.onSeatClick(it) },
                    onPayClick = {
                        viewModel.onPayment(
                            comission = 10
                        )
                    }
                )
            }
            state.error != null -> {
                Text(state.error!!)
            }
            state.hallScheme == null -> {
                Text(stringResource(R.string.error))
            }
        }
    }
}


package com.ticket.booking.presentation.navigation

sealed class Screen(val route: String) {
    object Hall : Screen("hall")
    object Payment : Screen("payment")
} 
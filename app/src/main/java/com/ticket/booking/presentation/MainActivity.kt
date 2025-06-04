package com.ticket.booking.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ticket.booking.presentation.navigation.Screen
import com.ticket.booking.presentation.screens.hall.HallScreen
import com.ticket.booking.presentation.theme.BookingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookingTheme {
                val navController = rememberNavController()
                
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Hall.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Hall.route) {
                            HallScreen(
                                navController = navController
                            )
                        }
                        composable(Screen.Payment.route) {
                            // TODO: PaymentScreen()
                        }
                    }
                }
            }
        }
    }
}

package com.ticket.booking.presentation

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ticket.booking.R
import com.ticket.booking.data.remote.HallResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is HallUiState.Loading -> showLoading()
                        is HallUiState.Success -> showData(state.data)
                        is HallUiState.Error -> showError(state.message)
                    }
                }
            }
        }

        viewModel.loadHallScheme()
    }

    private fun showLoading() {
        // Показать индикатор загрузки
    }

    private fun showData(data: HallResponse) {
        val textView = findViewById<TextView>(R.id.textViewResult)
        textView.text = data.has_started_text
    }

    private fun showError(message: String) {
        // Показать ошибку
    }
}

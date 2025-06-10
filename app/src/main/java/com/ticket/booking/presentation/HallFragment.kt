package com.ticket.booking.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ticket.booking.data.remote.HallResponse
import com.ticket.booking.data.remote.SeatsType
import com.ticket.booking.data.toUiSeat
import com.ticket.booking.databinding.FragmentHallBinding
import kotlinx.coroutines.launch

class HallFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentHallBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("HallFragment", "View Created. Setting up observers...")
        setupObservers()
        Log.d("HallFragment", "observers set.")
        setupClickListeners()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                Log.d("HallFragment", "Lifecycle started")
                viewModel.uiState.collect { state ->
                    when (state) {
                        is HallUiState.Success -> {
                            Log.d("HallFragment", "uiState success")
                            displayHallData(state.data)
                        }
                        is HallUiState.Error -> {
                            Log.d("HallFragment", "uiState error ${state.message}")
                            showError(state.message)
                        }
                        else -> {
                            Log.d("HallFragment", "uiState unknown")
                            showError("Error")
                        }
                    }
                }
            }
        }
    }

    private fun displayHallData(data: HallResponse) {
        binding.hallName.text = data.hall_name
        binding.sessionDateTime.text = data.session_time
        val seatsTypeList = data.seats_type.plus(
            SeatsType(
                name = "Занято",
                price = 0,
                seat_type = "",
                ticket_id = 0,
                ticket_type = ""
            )
        )
        val adapter = SeatTypeAdapter(seatsTypeList)
        binding.seatsTypeList.adapter = adapter

        val uiSeats = data.seats.map { it.toUiSeat() }
        binding.hallMapView.setMapData(
            seats = uiSeats,
            mapWidth = data.map_width,
            mapHeight = data.map_height
        )

    }

    private fun setupClickListeners() {
//        binding.btnProceedToPayment.setOnClickListener {
//            findNavController().navigate(R.id.action_hall_to_payment)
//        }
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
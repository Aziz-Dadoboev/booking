package com.ticket.booking.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.ticket.booking.R
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
        setupObservers()
        setupInteractions()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is HallUiState.Success -> {
                            val prepared = viewModel.prepareUi(state.data)
                            displayPreparedData(prepared)
                        }
                        is HallUiState.Error -> showError(state.message)
                        is HallUiState.Loading -> {
                        }
                    }
                }
            }
        }
    }

    private fun displayPreparedData(data: PreparedHallUi) {
        binding.hallName.text = data.hallName
        binding.sessionDateTime.text = data.sessionTime
        binding.seatsCnt.text = getString(R.string.booked_seats, data.bookedSeats.toString())

        if (data.hasStarted) binding.sessionStarted.text = data.hasStartedText
        else binding.sessionStarted.visibility = View.INVISIBLE

        binding.seatsTypeList.adapter = SeatTypeAdapter(data.seatTypes)

        binding.hallMapView.setMapData(
            seats = data.uiSeats,
            mapWidth = data.mapWidth,
            mapHeight = data.mapHeight
        )
    }

    private fun setupInteractions() {
        val hallMapView = binding.hallMapView
        val footerLayout = binding.footerLayout
        val selectedSeatsCountText = binding.selectedSeatsCount
        val totalAmountText = binding.totalAmount
        val payButton = binding.payButton

        hallMapView.onSeatSelectionChanged = {
            val selectedSeats = hallMapView.getSelectedSeats()
            val footerState = viewModel.calculateFooterState(selectedSeats)

            footerLayout.visibility = if (footerState.visible) View.VISIBLE else View.GONE
            selectedSeatsCountText.text = getString(R.string.selected_seats, footerState.count.toString())
            totalAmountText.text = getString(R.string.sum, footerState.total.toString())
        }

        payButton.setOnClickListener {
            findNavController().navigate(R.id.action_HallFragment_to_PaymentFragment)
        }
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
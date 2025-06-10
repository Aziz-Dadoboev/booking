package com.ticket.booking.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ticket.booking.R
import com.ticket.booking.databinding.FragmentPaymentBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PaymentFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var labelSum: TextView
    private lateinit var valueSum: TextView
    private lateinit var labelCommission: TextView
    private lateinit var buyButton: View
    private lateinit var ofertaText: TextView

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        labelSum = binding.labelSum
        valueSum = binding.valueSum
        labelCommission = binding.labelCommission
        buyButton = binding.buyButton
        ofertaText = binding.ofertaText

        observeState()
        setupClickListeners()
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.totalAmount.collectLatest { totalAmount ->
                valueSum.text = "$totalAmount"
                buyButton.contentDescription = getString(R.string.sum) + ": $totalAmount₽"
            }
        }
    }

    private fun setupClickListeners() {
        buyButton.setOnClickListener {
            findNavController().popBackStack()
        }

        ofertaText.setOnClickListener {
            val url = "https://api-life3.megafon.tj/static/Oferta-Echipta-ru.pdf"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}
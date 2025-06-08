package com.ticket.booking.presentation.screens.payment

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ticket.booking.R
import com.ticket.booking.presentation.navigation.Screen
import com.ticket.booking.presentation.screens.hall.HallViewModel

@Composable
fun PaymentScreen(
    navController: NavController
) {
    val parentEntry = remember(navController.currentBackStackEntry) {
        navController.getBackStackEntry(Screen.Hall.route)
    }
    val viewModel: HallViewModel = hiltViewModel(parentEntry)
    val totalAmount by viewModel.totalAmount.collectAsState()
    val commissionPercent by viewModel.commissionPercent.collectAsState()

    val context = LocalContext.current
    val commission = remember(totalAmount, commissionPercent) {
        totalAmount * commissionPercent / 100
    }
    val totalToPay = remember(totalAmount, commission) {
        totalAmount + commission
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Column {
            Text(
                text = stringResource(R.string.pay),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            PaymentRow(
                label = stringResource(R.string.sum_to_pay),
                value = "${totalAmount}",
                modifier = Modifier.padding(bottom = 8.dp)
            )

            PaymentRow(
                label = stringResource(R.string.comission, commissionPercent),
                value = "${commission}",
                modifier = Modifier.padding(bottom = 16.dp)
            )
            PaymentRow(
                label = stringResource(R.string.total_payment),
                value = "${totalToPay}",
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Column {
            Button(
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(stringResource(R.string.buy))
            }

            val annotatedString = buildAnnotatedString {
                append(stringResource(R.string.oferta))

                pushStringAnnotation(
                    tag = "OFFERTA_LINK",
                    annotation = "https://api-life3.megafon.tj/static/Oferta-Echipta-ru.pdf"
                )
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(stringResource(R.string.offerta_end))
                }
                pop()
            }

            Text(
                text = annotatedString,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api-life3.megafon.tj/static/Oferta-Echipta-ru.pdf"))
                        context.startActivity(intent)
                    },
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )
        }
    }
}

@Composable
private fun PaymentRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = textStyle,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = textStyle,
            fontWeight = FontWeight.Medium
        )
    }
}
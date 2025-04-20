package com.example.designsystem.exchangerate

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.designSystem.R

@Composable
fun ExchangeRate(
    modifier: Modifier = Modifier,
    exchangeRate: String
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.exchange_rate),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Text(
            text = exchangeRate,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
    }
}
package com.example.designsystem.balansinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.designSystem.R
import com.example.designsystem.buttons.TextButton

@Composable
fun BalanceInfo(
    modifier: Modifier = Modifier,
    balance: String,
    onAddMoneyClick: () -> Unit,
    onAddTransactionCLick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(R.drawable.btc),
            contentDescription = "Bitcoin Icon"
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = balance,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                iconRsId = R.drawable.ic_24_plus,
                text = stringResource(R.string.add_money),
                onClick = onAddMoneyClick
            )
            TextButton(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                iconRsId = R.drawable.ic_24_double_arrow_right,
                text = stringResource(R.string.add_transaction),
                onClick = onAddTransactionCLick
            )
        }
    }
}
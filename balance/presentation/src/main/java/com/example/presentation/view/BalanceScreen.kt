package com.example.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.common.util.formatDayMonth
import com.example.common.util.formatTime
import com.example.designSystem.R
import com.example.designsystem.bottomsheet.ModalBottomSheet
import com.example.designsystem.buttons.TextButton
import com.example.designsystem.transactionitems.DateItem
import com.example.designsystem.transactionitems.TransactionItem
import com.example.domain.model.Transaction
import com.example.presentation.model.BalanceUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BalanceScreen(
    modifier: Modifier = Modifier,
    uiState: State<BalanceUiState>,
    transactions: LazyPagingItems<Transaction>,
    onAddTransactionCLick: () -> Unit,
    onAddMoneyCLick: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val sheetState = rememberModalBottomSheetState()
    val focusRequester = remember { FocusRequester() }
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            Surface(
                shadowElevation = 4.dp
            ) {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    title = {
                        Text(
                            text = stringResource(R.string.balance),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black
                        )
                    },
                    actions = {
                        Column(
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text(
                                text = "Exchange Rate",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                            Text(
                                text = uiState.value.exchangeRate,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        }
                    }
                )
            }
        },
        content = { padding ->
            Column(
                modifier = modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Spacer(Modifier.height(16.dp))
                    Image(
                        modifier = Modifier
                            .size(64.dp)
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(R.drawable.btc),
                        contentDescription = "icon bitcoin"
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        text = uiState.value.amount,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            iconRsId = R.drawable.ic_24_plus,
                            text = stringResource(R.string.add_money),
                            onClick = { showBottomSheet = true }
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

                Spacer(Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(
                        count = transactions.itemCount,
                        itemContent = { index ->
                            val transaction = transactions[index] ?: return@items

                            when (transaction.transactionType) {
                                "recharge" -> {
                                    val time = transaction.timestamp.formatTime()
                                    TransactionItem(
                                        iconResId = R.drawable.ic_24_double_arrow_down,
                                        text = transaction.category,
                                        time = time,
                                        amount = "+${transaction.amount} BTC",
                                        color = Color.Green
                                    )
                                }

                                "withdraw" -> {
                                    val time = transaction.timestamp.formatTime()
                                    TransactionItem(
                                        iconResId = R.drawable.ic_24_double_arrow_up,
                                        text = transaction.category,
                                        time = time,
                                        amount = "-${transaction.amount} BTC",
                                        color = Color.Red
                                    )
                                }

                                "date" -> {
                                    val date = transaction.timestamp.formatDayMonth()
                                    DateItem(date = date)
                                }

                                else -> {}
                            }
                        }
                    )
                }
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = { showBottomSheet = false },
                    content = {
                        val regex = "^\\d+(\\.)?\\d*\$".toRegex()
                        var amount by remember { mutableStateOf(TextFieldValue(text = "")) }

                        LaunchedEffect(sheetState.currentValue) {
                            if (sheetState.currentValue == SheetValue.Expanded) {
                                focusRequester.requestFocus()
                                keyboardController?.show()
                            }
                        }

                        Column(
                            modifier = Modifier
                                .padding(vertical = 24.dp, horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.add_bitcoins),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Black
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                OutlinedTextField(
                                    modifier = Modifier
                                        .weight(1f)
                                        .focusRequester(focusRequester),
                                    value = amount,
                                    onValueChange = { input ->
                                        if (input.text.isEmpty() || regex.matches(input.text)) {
                                            amount = input
                                        }
                                    },
                                    placeholder = {
                                        Text(
                                            text = stringResource(R.string.enter_amount),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Gray
                                        )
                                    },
                                    leadingIcon = {
                                        Image(
                                            modifier = Modifier.size(32.dp),
                                            painter = painterResource(R.drawable.btc),
                                            contentDescription = "icon bitcoin"
                                        )
                                    },
                                    shape = RoundedCornerShape(16.dp),
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Done
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            showBottomSheet = false
                                            keyboardController?.hide()
                                            onAddMoneyCLick(amount.text)
                                        }
                                    )
                                )
                                TextButton(
                                    modifier = Modifier
                                        .height(48.dp),
                                    text = stringResource(R.string.add),
                                    onClick = {
                                        showBottomSheet = false
                                        keyboardController?.hide()
                                        onAddMoneyCLick(amount.text)
                                    }
                                )
                            }
                        }
                    }
                )
            }
        }
    )
}
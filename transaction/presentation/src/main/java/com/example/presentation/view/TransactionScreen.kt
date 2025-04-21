package com.example.presentation.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.common.model.TransactionCategory
import com.example.designSystem.R
import com.example.designsystem.bottomsheet.ModalBottomSheet
import com.example.designsystem.buttons.TextButton
import com.example.presentation.model.Category
import com.example.presentation.model.TransactionUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TransactionScreen(
    modifier: Modifier = Modifier,
    uiState: State<TransactionUiState>,
    onBackPressedCLick: () -> Unit,
    onAddTransactionCLick: (String, Category?) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val sheetState = rememberModalBottomSheetState(true)
    val focusRequester = remember { FocusRequester() }
    var showBottomSheet by remember { mutableStateOf(false) }
    val regex = "^\\d+(\\.)?\\d*\$".toRegex()
    val selectedCategory = remember { mutableStateOf<Category?>(null) }
    var amount by remember { mutableStateOf(TextFieldValue(text = "")) }

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
                            text = stringResource(R.string.transaction),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onBackPressedCLick
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_24_arrow_left),
                                tint = Color.Black,
                                contentDescription = "Button Back"
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
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AnimatedVisibility(
                    visible = uiState.value.isError
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                            .height(64.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.Red.copy(0.1f)),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_24_alert_circle),
                                tint = Color.Red,
                                contentDescription = "Warning Icon"
                            )
                            Text(
                                text = stringResource(uiState.value.errorTextResId),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clickable { showBottomSheet = true }
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    selectedCategory.value?.iconResId?.let { iconResId ->
                        Icon(
                            painter = painterResource(iconResId),
                            tint = Color.Black,
                            contentDescription = "Selected Category Icon"
                        )
                    }
                    Text(
                        modifier = Modifier.weight(1f),
                        text = selectedCategory.value?.categoryResId?.let { stringResource(it) }
                            ?: stringResource(R.string.choose_category),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_24_arrow_right),
                        tint = Color.Black,
                        contentDescription = "Right Icon"
                    )
                }
                OutlinedTextField(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = amount,
                    onValueChange = { input ->
                        if (input.text.isEmpty() || (regex.matches(input.text) && input.text.length <= 10)) {
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
                            contentDescription = "Bitcoin Icon"
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    )
                )
                Spacer(Modifier.height(16.dp))
                TextButton(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    text = stringResource(R.string.add),
                    onClick = {
                        showBottomSheet = false
                        keyboardController?.hide()
                        onAddTransactionCLick(
                            amount.text,
                            selectedCategory.value
                        )
                    }
                )
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = { showBottomSheet = false },
                    content = {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 24.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = stringResource(R.string.choose_category),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Black
                            )
                            Spacer(Modifier.height(8.dp))
                            uiState.value.categories.forEach { category ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp)
                                        .clickable {
                                            selectedCategory.value = category
                                            showBottomSheet = false
                                        }
                                        .padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(category.iconResId),
                                        tint = Color.Black,
                                        contentDescription = "Category Icon"
                                    )
                                    Text(
                                        text = stringResource(category.categoryResId),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    )
}
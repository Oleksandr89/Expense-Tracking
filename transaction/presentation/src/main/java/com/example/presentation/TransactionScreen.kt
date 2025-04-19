package com.example.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun TransactionScreen(
    modifier: Modifier = Modifier,
    onBackPressedCLick: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {

        },
        content = { padding ->
            Column(
                modifier = modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {


            }
        }
    )
}
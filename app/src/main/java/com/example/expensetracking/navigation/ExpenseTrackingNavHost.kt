package com.example.expensetracking.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expensetracking.model.ScreenNavItem
import com.example.presentation.BalanceRoute
import com.example.presentation.TransactionRoute


@Composable
fun ExpenseTrackingNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ScreenNavItem.BalanceScreen
    ) {
        composable<ScreenNavItem.BalanceScreen> {
            BalanceRoute(
                onAddTransactionCLick = {
                    navController.navigate(ScreenNavItem.TransactionScreen)
                }
            )
        }
        composable<ScreenNavItem.TransactionScreen> {
            TransactionRoute(
                onBackPressedCLick = {
                    navController.navigateUp()
                }
            )
        }
    }
}
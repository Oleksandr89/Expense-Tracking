package com.example.domain.model

import com.example.database.model.BalanceEntity
import com.example.common.util.toBigDecimal
import java.math.BigDecimal

data class BalanceModel(
    val amount: BigDecimal,
    val amountStr: String,
)

fun BigDecimal.toBalanceModel() = BalanceModel(
    amount = this,
    amountStr = this.toPlainString()
)

fun BalanceModel.toBalanceEntity() = BalanceEntity(
    amountStr = amountStr
)

fun BalanceEntity?.toBalanceModel() = BalanceModel(
    amount = this?.amountStr?.toBigDecimal() ?: "0".toBigDecimal(),
    amountStr = this?.amountStr ?: "0"
)
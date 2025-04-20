package com.example.common.util

import java.math.BigDecimal
import java.math.RoundingMode

fun String.toBigDecimal(): BigDecimal {
    return BigDecimal(this).setScale(2, RoundingMode.HALF_UP)
}
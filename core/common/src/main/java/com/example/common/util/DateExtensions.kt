package com.example.common.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val dayFormatter = DateTimeFormatter.ofPattern("dd MMMM")
private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

fun Long.formatDayMonth(): String {
    return try {
        val date = Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        date.format(dayFormatter)
    } catch (exception: Exception) {
        ""
    }
}

fun Long.formatTime(): String {
    return try {
        val time = Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalTime()
        time.format(timeFormatter)
    } catch (exception: Exception) {
        ""
    }
}
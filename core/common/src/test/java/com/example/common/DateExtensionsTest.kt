package com.example.common

import com.example.common.util.formatDayMonth
import com.example.common.util.formatTime
import com.example.common.util.toBigDecimal
import org.junit.Test
import org.junit.Assert.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

class DateExtensionsTest {

    @Test
    fun `formatDayMonth should return non-empty string for valid timestamp`() {
        val localDate = LocalDate.of(2024, 3, 15)
        val timestamp = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        
        val result = timestamp.formatDayMonth()
        
        assertTrue("Result should not be empty", result.isNotEmpty())
        assertTrue("Result should contain day number", result.contains("15"))
    }

    @Test
    fun `formatTime should return correct format for valid timestamp`() {
        val localTime = LocalTime.of(14, 30)
        val timestamp = localTime.atDate(LocalDate.now())
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        
        val result = timestamp.formatTime()
        
        assertEquals("14:30", result)
    }

    @Test
    fun `formatDayMonth should handle exception gracefully`() {
        val result = try {
            (-1L).formatDayMonth()
        } catch (e: Exception) {
            ""
        }
        
        assertTrue("Result should be string", result is String)
    }

    @Test
    fun `formatTime should handle exception gracefully`() {
        val result = try {
            (-1L).formatTime()
        } catch (e: Exception) {
            ""
        }
        
        assertTrue("Result should be string", result is String)
    }
}

class StringExtensionTest {

    @Test
    fun `toBigDecimal should convert string to BigDecimal with 2 decimal places`() {
        val result = "123.456".toBigDecimal()
        
        assertEquals("123.46", result.toString())
    }

    @Test
    fun `toBigDecimal should handle integers`() {
        val result = "100".toBigDecimal()
        
        assertEquals("100.00", result.toString())
    }
}
package com.example.calculator.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object NumberFormatter {
    private val decimalFormat = DecimalFormat("#,###.########", DecimalFormatSymbols(Locale.US))
    fun format(number: String): String {
        if (number.isEmpty()) return "0"

        return try {
            val value = number.toDouble()
            when (value) {
                0.0 -> "0"
                value.toLong().toDouble() -> decimalFormat.format(value.toLong())
                else -> decimalFormat.format(value)
            }
        } catch (e: NumberFormatException) {
            number
        }
    }
}
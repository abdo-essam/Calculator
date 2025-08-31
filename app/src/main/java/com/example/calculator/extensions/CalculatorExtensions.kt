package com.example.calculator.extensions

import android.widget.Button
import com.example.calculator.model.CalculatorAction
import com.example.calculator.model.CalculatorNumber
import com.example.calculator.model.CalculatorOperation

// Button text to enum conversions
fun Button.toCalculatorNumber(): CalculatorNumber? {
    return CalculatorNumber.fromValue(text.toString())
}

fun Button.toCalculatorOperation(): CalculatorOperation? {
    return CalculatorOperation.fromSymbol(text.toString())
}

fun Button.toCalculatorAction(): CalculatorAction? {
    return CalculatorAction.fromSymbol(text.toString())
}

// String extensions for number validation
fun String.isValidNumber(): Boolean {
    return this.toDoubleOrNull() != null
}

fun String.isValidDecimalNumber(): Boolean {
    return matches(Regex("^-?\\d*\\.?\\d*$"))
}

// Double extensions for formatting
fun Double.toCalculatorString(): String {
    return when {
        this == this.toLong().toDouble() -> this.toLong().toString()
        else -> this.toString()
    }
}
package com.example.calculator.state

data class CalculatorState(
    val displayValue: String,
    val expression: String,
    val isError: Boolean = false
)